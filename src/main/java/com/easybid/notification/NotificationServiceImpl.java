package com.easybid.notification;

import com.easybid.mapper.NotificationMapper;
import com.easybid.notification.dto.CreateNotificationDTO;
import com.easybid.notification.dto.NotificationResponseDTO;
import com.easybid.notification.dto.UpdateNotificationDTO;
import com.easybid.user.UserEntity;
import com.easybid.user.UserRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

  private final NotificationRepository notificationRepository;
  private final UserRepository userRepository;

  @Autowired
  public NotificationServiceImpl(NotificationRepository notificationRepository, UserRepository userRepository) {
    this.notificationRepository = notificationRepository;
    this.userRepository = userRepository;
  }

  @Override
  public NotificationResponseDTO createNotification(CreateNotificationDTO createNotificationDTO) {
    UserEntity user = userRepository.findById(createNotificationDTO.getUserId())
        .orElseThrow(
            () -> new IllegalArgumentException("User not found with ID: " + createNotificationDTO.getUserId()));
    NotificationEntity notification = new NotificationEntity();
    notification.setUser(user);
    notification.setMessage(createNotificationDTO.getMessage());
    notification.setType(createNotificationDTO.getType());
    NotificationEntity savedNotification = notificationRepository.save(notification);
    return NotificationMapper.toNotificationResponseDTO(savedNotification);
  }

  @Override
  public NotificationResponseDTO getNotificationById(UUID notificationId) {
    NotificationEntity notification = notificationRepository.findById(notificationId)
        .orElseThrow(() -> new IllegalArgumentException("Notification not found with ID: " + notificationId));

    return NotificationMapper.toNotificationResponseDTO(notification);
  }

  @Override
  public List<NotificationResponseDTO> getNotificationsByUserId(UUID userId) {
    List<NotificationEntity> notifications = notificationRepository.findAllByUserId(userId);

    return notifications.stream()
        .map(NotificationMapper::toNotificationResponseDTO)
        .collect(Collectors.toList());
  }

  @Override
  public NotificationResponseDTO updateNotificationStatus(UUID notificationId,
      UpdateNotificationDTO updateNotificationDTO) {
    NotificationEntity notification = notificationRepository.findById(notificationId)
        .orElseThrow(() -> new IllegalArgumentException("Notification not found with ID: " + notificationId));

    notification.setStatus(updateNotificationDTO.getStatus());
    NotificationEntity updatedNotification = notificationRepository.save(notification);
    return NotificationMapper.toNotificationResponseDTO(updatedNotification);
  }

}
