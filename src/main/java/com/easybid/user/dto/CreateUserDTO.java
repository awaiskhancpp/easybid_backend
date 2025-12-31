package com.easybid.user.dto;

import com.easybid.enums.UsersRole;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for creating a user")
public class CreateUserDTO {

  @NotBlank(message = "Name must not be null or blank")
  @Schema(description = "Name of the user", example = "John Doe")
  @Size(max = 255, min = 1)
  private String name;

  @Email(message = "Invalid email format")
  @NotBlank(message = "Email must not be null or blank")
  @Schema(description = "Email of the user", example = "john.doe@example.com")
  @Size(max = 255, min = 1)
  private String email;

  @NotBlank(message = "Password must not be null or blank")
  @Schema(description = "Password of the user", example = "securePassword123")
  @Size(max = 255, min = 1)
  private String password;

  @Schema(description = "Address of the user", example = "123 Main St, Cityville")
  @Size(max = 255, min = 1)
  private String address;

  @Schema(description = "Phone number of the user", example = "123-456-7890")
  @Size(max = 255)
  private String phoneNumber;

  @Schema(description = "Bio of the user", example = "I love collecting vintage items and participating in auctions.")
  @Size(max = 255)
  private String bio;

  @NotNull(message = "Role must not be null")
  @Schema(description = "Role of the user", example = "USER")
  private UsersRole role;
}
