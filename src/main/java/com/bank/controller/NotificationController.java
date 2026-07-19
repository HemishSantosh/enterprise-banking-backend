package com.bank.controller;

import com.bank.entity.Notification;
import com.bank.entity.User;
import com.bank.repository.UserRepository;
import com.bank.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final UserRepository userRepository;

    private User getCurrentUser(Authentication authentication) {

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @GetMapping
    public ResponseEntity<List<Notification>> getNotifications(
            Authentication authentication) {

        User user = getCurrentUser(authentication);

        return ResponseEntity.ok(
                notificationService.getNotifications(user)
        );
    }

    @GetMapping("/unread-count")
    public ResponseEntity<Map<String, Long>> unreadCount(
            Authentication authentication) {

        User user = getCurrentUser(authentication);

        return ResponseEntity.ok(
                Map.of(
                        "count",
                        notificationService.getUnreadCount(user)
                )
        );
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<String> markRead(
            @PathVariable Long id) {

        notificationService.markAsRead(id);

        return ResponseEntity.ok("Notification marked as read");
    }

    @PutMapping("/read-all")
    public ResponseEntity<String> markAllRead(
            Authentication authentication) {

        User user = getCurrentUser(authentication);

        notificationService.markAllAsRead(user);

        return ResponseEntity.ok("All notifications marked as read");
    }
}