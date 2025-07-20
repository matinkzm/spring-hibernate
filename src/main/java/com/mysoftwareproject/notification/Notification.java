package com.mysoftwareproject.notification;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "notificationId")
    @SequenceGenerator(name = "notificationId", sequenceName = "notificationId", allocationSize = 1)
    private Integer id;
    private Integer receiverId;
    private String message;
    private String notificationTime;
    private String readStatus;


    public Notification(Integer receiverId, String message, String date, String readStatus) {
        this.receiverId = receiverId;
        this.message = message;
        this.notificationTime = date;
        this.readStatus = readStatus;
    }
}
