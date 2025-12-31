package com.easybid.user.dto;

import com.easybid.common.BaseResponseDto;
import com.easybid.enums.UsersRole;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for user responses")
public class UserResponseDTO extends BaseResponseDto {

  @Schema(description = "Name of the user", example = "John Doe")
  private String name;

  @Schema(description = "Email of the user", example = "john.doe@example.com")
  private String email;

  @Schema(description = "Address of the user", example = "123 Main St, Anytown, USA")
  private String address;

  @Schema(description = "Phone number of the user", example = "+1234567890")
  private String phoneNumber;

  @Schema(description = "Role of the user", example = "USER")
  private UsersRole role;

  @Schema(description = "Bio of the user", example = "I love collecting vintage items and participating in auctions.")
  private String bio;
}
