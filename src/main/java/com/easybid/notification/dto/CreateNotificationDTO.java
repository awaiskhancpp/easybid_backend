package com.easybid.notification.dto;

import java.util.UUID;

import com.easybid.enums.NotificationType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for creating a notification")
public class CreateNotificationDTO {

  @NotNull(message = "userId must not be null")
  @Schema(description = "Unique identifier of the user", example = "123e4567-e89b-12d3-a456-426614174000")
  private UUID userId;

  @NotBlank(message = "Message must not be blank")
  @Schema(description = "Message content of the notification", example = "Your bid has been updated")
  private String message;

  @NotNull(message = "Type must not be null")
  @Schema(description = "Type of the notification", example = "BID_UPDATE")
  private NotificationType type;
}
