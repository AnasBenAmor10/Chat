package com.example.Chat.repository;

import com.example.Chat.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room,Integer> {
//    @Override
//    Optional<Room> findById(Integer integer);
}
