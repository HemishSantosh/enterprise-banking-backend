package com.bank.service;

import com.bank.entity.Notification;
import com.bank.entity.User;
import com.bank.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    // Get all notifications
    public List<Notification> getNotifications(User user) {
        return notificationRepository.findByUserOrderByCreatedAtDesc(user);
    }

    // Count unread notifications
    public long getUnreadCount(User user) {
        return notificationRepository.countByUserAndReadFalse(user);
    }

    // Mark one notification as read
    public void markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        notification.setRead(true);
        notificationRepository.save(notification);
    }

    // Mark all notifications as read
    public void markAllAsRead(User user) {
        List<Notification> notifications =
                notificationRepository.findByUserAndReadFalse(user);

        notifications.forEach(notification -> notification.setRead(true));

        notificationRepository.saveAll(notifications);
    }

    // Create a new notification
    public Notification createNotification(
            User user,
            String title,
            String message,
            String type
    ) {

        Notification notification = Notification.builder()
                .user(user)
                .title(title)
                .message(message)
                .notificationType(
                        Enum.valueOf(
                                com.bank.entity.NotificationType.class,
                                type.toUpperCase()
                        )
                )
                .read(false)
                .createdAt(LocalDateTime.now())
                .build();

        return notificationRepository.save(notification);
    }
}