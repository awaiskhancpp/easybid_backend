package com.easybid.exceptions;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationErrorResponse {
  private String message;
  private int statusCode;
  private LocalDateTime timestamp;
  private List<String> errors;
}
