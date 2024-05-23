package com.kusitms29.backendH.domain.chat.repository;

import com.kusitms29.backendH.domain.chat.entity.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoomRepository extends MongoRepository<Room, String> {
    Optional<Room> findByRoomName(String roomName);
}
