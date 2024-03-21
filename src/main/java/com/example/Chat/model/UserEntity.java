package com.example.Chat.model;

// import Packages
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

//User Model
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    // Email should be unique
    @Column(
            nullable = false,
            unique = true
    )
    private String email;

    private String status;

    @Column (
            nullable = false
    )
    private String password;

    private LocalDateTime createdAt = LocalDateTime.now();

    //Relationship between User and Messages (One User sends one Message)
    @OneToOne
    @JoinColumn(name = "message_id")
    private Message message;

    //Relationship between User and Room ( Many user in Many Room)
    @ManyToMany
    @JoinTable(
            name = "User_Room",
            joinColumns =@JoinColumn (name = "user_id" , referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "room_id", referencedColumnName = "id")
    )
    private List<Room> rooms;

    @ManyToMany (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id"))
    private List<Role> roles = new ArrayList<>();

}
