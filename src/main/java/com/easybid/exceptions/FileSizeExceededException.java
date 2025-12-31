package com.easybid.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
public class FileSizeExceededException extends RuntimeException {
  public FileSizeExceededException(String message) {
    super(message);
  }
}
