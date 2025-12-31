package com.easybid.notification;

import com.easybid.notification.dto.CreateNotificationDTO;
import com.easybid.notification.dto.NotificationResponseDTO;
import com.easybid.notification.dto.UpdateNotificationDTO;

import java.util.List;
import java.util.UUID;

public interface NotificationService {

  NotificationResponseDTO createNotification(CreateNotificationDTO createNotificationDTO);

  NotificationResponseDTO getNotificationById(UUID notificationId);

  List<NotificationResponseDTO> getNotificationsByUserId(UUID userId);

  NotificationResponseDTO updateNotificationStatus(UUID notificationId, UpdateNotificationDTO updateNotificationDTO);

}
