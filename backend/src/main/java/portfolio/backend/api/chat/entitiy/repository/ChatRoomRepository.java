package portfolio.backend.api.chat.entitiy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {
    List<ChatRoom> findAllByUserOrPostUser(Long user, Long postUser);
    Optional<ChatRoom> findByUserAndPostUser(Long user, Long postUser);

}
