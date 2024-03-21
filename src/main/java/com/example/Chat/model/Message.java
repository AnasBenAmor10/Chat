package com.example.Chat.model;

//import Packages
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String content;
    private LocalDateTime createdAt = LocalDateTime.now();

    // Relationship one to one between user and message
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    // Relationship Many to one between Message and Room (Many Message sent to one Room)
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

}
