package com.easybid.notification.dto;

import com.easybid.common.BaseResponseDto;
import com.easybid.enums.NotificationStatus;
import com.easybid.enums.NotificationType;
import com.easybid.user.dto.UserResponseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response DTO for notification")
public class NotificationResponseDTO extends BaseResponseDto {

  @Schema(description = "Unique identifier of the user")
  private UserResponseDTO user;

  @Schema(description = "Message content of the notification", example = "Your bid has been updated")
  private String message;

  @Schema(description = "Status of the notification", example = "UNREAD")
  private NotificationStatus status;

  @Schema(description = "Type of the notification", example = "BID_UPDATE")
  private NotificationType type;
}
