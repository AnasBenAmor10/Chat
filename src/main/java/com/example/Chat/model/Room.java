package com.example.Chat.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String roomName;
    private LocalDateTime createdAt = LocalDateTime.now();


    //Relationship between Room and Messages (One Room contain Many Messages)
    @OneToMany (mappedBy = "room")
    private List <Message> messages;


    //Relationship between Room and User
    @ManyToMany (mappedBy = "rooms")
    private List<UserEntity> users;

}