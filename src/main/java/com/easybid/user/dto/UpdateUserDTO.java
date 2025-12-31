package com.easybid.user.dto;

import com.easybid.enums.UsersRole;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for updating an existing user")
public class UpdateUserDTO {

  @Pattern(regexp = ".*\\S.*", message = "Name must not be blank")
  @Schema(description = "Updated name of the user", example = "John Doe")
  private String name;

  @Email(message = "Invalid email format")
  @Schema(description = "Updated email of the user", example = "john.doe@example.com")
  private String email;

  @Pattern(regexp = ".*\\S.*", message = "Password must not be blank")
  @Schema(description = "Updated password of the user", example = "newSecurePassword123")
  private String password;

  @Pattern(regexp = ".*\\S.*", message = "Address must not be blank")
  @Schema(description = "Updated address of the user", example = "456 Elm St, Newtown, USA")
  @Size(max = 255)
  private String address;

  @Pattern(regexp = ".*\\S.*", message = "Phone number must not be blank")
  @Schema(description = "Updated phone number of the user", example = "+9876543210")
  private String phoneNumber;

  @Pattern(regexp = ".*\\S.*", message = "Bio must not be blank")
  @Schema(description = "Bio of the user", example = "I love collecting vintage items and participating in auctions.")
  @Size(max = 255)
  private String bio;

  @Schema(description = "Updated role of the user", example = "BUYER")
  private UsersRole role;
}
