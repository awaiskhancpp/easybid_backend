package com.easybid.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
public class UnsupportedFileFormatException extends RuntimeException {
  public UnsupportedFileFormatException(String message) {
    super(message);
  }
}
