package com.mysoftwareproject.notification;

import com.mysoftwareproject.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    public void createNotification(Integer sponsorId, String message) {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        String formattedDate = myDateObj.format(myFormatObj);
        Notification tempNotification = new Notification(sponsorId,message,formattedDate, "false");
        notificationRepository.save(tempNotification);
    }

    public List<Notification> findAllByReceiverId(Integer receiverId) {
        return notificationRepository.findAllByReceiverId(receiverId);
    }

    public Notification updateNotification(Integer id, Notification notification) {
        Notification foundNotification = notificationRepository.findById(id).orElse(null);
        boolean flag = false;

        if (foundNotification != null) {
            if (notification.getReadStatus() != null){
                foundNotification.setReadStatus(notification.getReadStatus());
                flag = true;
            }
            if (notification.getMessage() != null){
                foundNotification.setMessage(notification.getMessage());
                flag = true;
            }
            if (notification.getReceiverId() != null){
                foundNotification.setReceiverId(notification.getReceiverId());
                flag = true;
            }
            if (flag){
                LocalDateTime myDateObj = LocalDateTime.now();
                DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

                String formattedDate = myDateObj.format(myFormatObj);
                foundNotification.setNotificationTime(formattedDate);
            }
            return notificationRepository.save(foundNotification);
        }
        else
            throw new NotFoundException("Notification with id "+id+" not found");
    }

}
