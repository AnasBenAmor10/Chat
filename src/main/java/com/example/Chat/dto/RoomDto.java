package com.example.Chat.dto;

import lombok.Data;
import java.time.LocalDateTime;


@Data
public class RoomDto {
    private int id;
    private String roomName;
    private LocalDateTime createdAt = LocalDateTime.now();

}
