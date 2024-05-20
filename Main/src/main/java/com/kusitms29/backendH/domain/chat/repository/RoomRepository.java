package com.kusitms29.backendH.domain.chat.repository;

import com.kusitms29.backendH.domain.chat.entity.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomRepository extends MongoRepository<Room, String> {
}
