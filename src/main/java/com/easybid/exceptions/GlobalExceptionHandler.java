package com.easybid.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
    return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<ErrorResponse> handleNoHandlerFound(NoHandlerFoundException ex) {
    String errorMessage = String.format("The requested URL '%s' was not found on this server.", ex.getRequestURL());
    return buildErrorResponse(errorMessage, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(DataConflictException.class)
  public ResponseEntity<ErrorResponse> handleDataConflict(DataConflictException ex) {
    return buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(AccessForbiddenException.class)
  public ResponseEntity<ErrorResponse> handleForbidden(AccessForbiddenException ex) {
    return buildErrorResponse(ex.getMessage(), HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<ErrorResponse> handleUnauthorized(UnauthorizedException ex) {
    return buildErrorResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(AuthenticationFailedException.class)
  public ResponseEntity<ErrorResponse> handleAuthenticationFailed(AuthenticationFailedException ex) {
    return buildErrorResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(TokenExpiredException.class)
  public ResponseEntity<ErrorResponse> handleTokenExpired(TokenExpiredException ex) {
    return buildErrorResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(InvalidTokenException.class)
  public ResponseEntity<ErrorResponse> handleInvalidToken(InvalidTokenException ex) {
    return buildErrorResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex) {
    String errorMessage = "Invalid credentials. Please check your username and password.";
    return buildErrorResponse(errorMessage, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
    String errorMessage = "You do not have permission to access this resource.";
    return buildErrorResponse(errorMessage, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ValidationErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
    ValidationErrorResponse errorResponse = new ValidationErrorResponse(
        "Validation failed",
        HttpStatus.BAD_REQUEST.value(),
        LocalDateTime.now(),
        ex.getBindingResult().getFieldErrors().stream()
            .map(fieldError -> String.format("%s: %s", fieldError.getField(), fieldError.getDefaultMessage()))
            .collect(Collectors.toList()));
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
    String errorMessage = "Invalid request payload.";

    if (ex.getCause() instanceof InvalidFormatException) {
      InvalidFormatException cause = (InvalidFormatException) ex.getCause();

      if (cause.getTargetType().isEnum()) {
        String acceptedValues = Arrays.toString(cause.getTargetType().getEnumConstants());
        errorMessage = String.format(
            "Invalid value '%s' for field '%s'. Accepted values: %s.",
            cause.getValue(),
            cause.getPath().get(0).getFieldName(),
            acceptedValues);
      }
    }

    return buildErrorResponse(errorMessage, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException ex) {
    return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InternalServerErrorException.class)
  public ResponseEntity<ErrorResponse> handleInternalServerError(InternalServerErrorException ex) {
    return buildErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(FileSizeExceededException.class)
  public ResponseEntity<ErrorResponse> handleFileSizeExceeded(FileSizeExceededException ex) {
    return buildErrorResponse(ex.getMessage(), HttpStatus.PAYLOAD_TOO_LARGE);
  }

  @ExceptionHandler(UnsupportedFileFormatException.class)
  public ResponseEntity<ErrorResponse> handleUnsupportedFileFormat(UnsupportedFileFormatException ex) {
    return buildErrorResponse(ex.getMessage(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
  }

  @ExceptionHandler(FileStorageException.class)
  public ResponseEntity<ErrorResponse> handleFileStorage(FileStorageException ex) {
    return buildErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleAllOtherExceptions(Exception ex) {
    return buildErrorResponse("An unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private ResponseEntity<ErrorResponse> buildErrorResponse(String message, HttpStatus status) {
    ErrorResponse error = new ErrorResponse(message, status.value(), LocalDateTime.now());
    return ResponseEntity.status(status).body(error);
  }

}
