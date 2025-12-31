
package com.easybid.notification.dto;

import com.easybid.enums.NotificationStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for updating a notification")
public class UpdateNotificationDTO {

  @NotNull(message = "Status must not be null")
  @Schema(description = "Updated status of the notification", example = "READ")
  private NotificationStatus status;
}
