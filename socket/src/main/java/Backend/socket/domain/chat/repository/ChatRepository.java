package Backend.socket.domain.chat.repository;


import Backend.socket.domain.chat.domain.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRepository extends MongoRepository<Chat, String> {
}
