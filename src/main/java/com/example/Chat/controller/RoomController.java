package com.example.Chat.controller;

import com.example.Chat.model.Room;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class RoomController {

    //Get all Rooms
    @GetMapping("room")
    public ResponseEntity<List<Room>> getRooms() {
        List<Room> rooms = new ArrayList<>();
        // Create a test room
        Room room = new Room();
        room.setId(1);
        room.setRoomName("Test Room");
        room.setCreatedAt(LocalDateTime.now());
        // add a room into a list
        rooms.add(room);
        return ResponseEntity.ok(rooms);
    }


    //Get Room by ID
//    @GetMapping("/room/{id}")
//    public ResponseEntity<Room> getRoomById(@PathVariable int id) {
//        Optional<Room> room = RoomService.findById(id);
//        return room.map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }


    //Create a new Room
    @PostMapping("room/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        room.setCreatedAt(LocalDateTime.now());
        System.out.println(room.getRoomName());
        return new ResponseEntity<>(room, HttpStatus.CREATED);
    }

    //update a room
    @PutMapping ("room/{id}/update")
    public ResponseEntity<Room> updateRoom (@RequestBody Room room , @PathVariable("id") int roomId){
        System.out.println(room.getRoomName());
        return ResponseEntity.ok(room);
    }

    @DeleteMapping("room/{id}/delete")
    public ResponseEntity<String> deleteRoom(@PathVariable("id") int roomId){
        System.out.println(roomId);
        return ResponseEntity.ok("Room deleted successfully");
    }


}
