package com.easybid.mapper;

import com.easybid.notification.NotificationEntity;
import com.easybid.notification.dto.NotificationResponseDTO;

public class NotificationMapper {
  public static NotificationResponseDTO toNotificationResponseDTO(NotificationEntity notification) {
    if (notification == null) {
      return null;
    }
    NotificationResponseDTO responseDTO = new NotificationResponseDTO();
    responseDTO.setId(notification.getId());
    responseDTO.setCreatedAt(notification.getCreatedAt());
    responseDTO.setUpdatedAt(notification.getUpdatedAt());
    responseDTO.setDeletedAt(notification.getDeletedAt());
    responseDTO.setUser(UserMapper.toUserResponseDTO(notification.getUser()));
    responseDTO.setMessage(notification.getMessage());
    responseDTO.setStatus(notification.getStatus());
    responseDTO.setType(notification.getType());
    return responseDTO;
  }
}
