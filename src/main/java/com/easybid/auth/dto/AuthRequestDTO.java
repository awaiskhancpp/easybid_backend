package com.easybid.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Request object for authentication")
public class AuthRequestDTO {

  @Schema(description = "Email", example = "demo@gmail.com")
  @NotBlank(message = "Email is mandatory")
  @Email(message = "Email should be valid")
  private String email;

  @Schema(description = "Password", example = "password")
  @NotBlank(message = "Password is mandatory")
  private String password;

}
