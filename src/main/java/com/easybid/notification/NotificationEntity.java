package com.easybid.notification;

import com.easybid.common.BaseEntity;
import com.easybid.enums.NotificationStatus;
import com.easybid.enums.NotificationType;
import com.easybid.user.UserEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "notifications")
public class NotificationEntity extends BaseEntity {

  @NotNull
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  @JsonBackReference("user-notifications")
  private UserEntity user;

  @Column(name = "message", nullable = false)
  private String message;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private NotificationStatus status = NotificationStatus.UNREAD;

  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false)
  private NotificationType type;

}
