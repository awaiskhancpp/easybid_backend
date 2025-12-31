package com.easybid.auth.dto;

import com.easybid.user.dto.UserResponseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Auth Response DTO")
@Data
public class AuthResponseDTO {

  @Schema(description = "User")
  private UserResponseDTO user;

  @Schema(description = "Refresh Token")
  private String refreshToken;

  @Schema(description = "Access Token")
  private String accessToken;

}
