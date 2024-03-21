package com.example.Chat.service.impl;

import com.example.Chat.dto.RoomDto;
import com.example.Chat.model.Room;
import com.example.Chat.repository.RoomRepository;
import com.example.Chat.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomServiceImpl implements RoomService {
    private RoomRepository roomRepository;


    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public RoomDto createRoom(RoomDto roomDto){
        Room room = new Room();
        room.setRoomName(roomDto.getRoomName());

        Room newRoom = roomRepository.save(room);
        RoomDto roomResponse= new RoomDto();
        roomResponse.setId(newRoom.getId());
        roomResponse.setRoomName(newRoom.getRoomName());
        return roomResponse;
    }


}
