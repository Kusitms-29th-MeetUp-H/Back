package Backend.socket.domain.chat.repository;

import Backend.socket.domain.chat.domain.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomRepository extends MongoRepository<Room, String> {
}
