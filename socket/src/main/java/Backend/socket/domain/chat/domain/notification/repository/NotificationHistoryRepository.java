package Backend.socket.domain.chat.domain.notification.repository;

import Backend.socket.domain.chat.domain.notification.entity.NotificationHistory;
import Backend.socket.domain.chat.domain.notification.entity.TopCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationHistoryRepository extends JpaRepository<NotificationHistory, Long> {
}
