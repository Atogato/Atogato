package portfolio.backend.api.messenger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import portfolio.backend.api.messenger.entity.Message;
import portfolio.backend.authentication.api.entity.user.User;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findAllByReceiver(User user);
    List<Message> findAllBySender(User user);
}