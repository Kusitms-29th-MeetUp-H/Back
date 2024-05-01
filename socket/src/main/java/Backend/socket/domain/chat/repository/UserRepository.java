package Backend.socket.domain.chat.repository;

import Backend.socket.domain.chat.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBySessionId(String sessionId);
}
