package com.mysoftwareproject.notification;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

//    @PostMapping("/{sponsorId}/{message")
//    public Notification createNotification(@PathVariable Integer sponsorId, @PathVariable String message) {
//        return notificationService.createNotification(notification);
//    }

    @GetMapping("/getNotificationsByReceiverId/{receiverId}") // correct
    public List<Notification> getNotificationsByReceiverId(@PathVariable("receiverId") Integer receiverId) {
        return notificationService.findAllByReceiverId(receiverId);
    }

    @PutMapping("/{notificationID}") // correct
    public Notification updateNotification(@PathVariable("notificationID") Integer notificationID, @RequestBody Notification notification) {
        return notificationService.updateNotification(notificationID, notification);
    }

    @GetMapping
    public List<Notification> getAllNotifications() {
        return notificationService.findAll();
    }
}
