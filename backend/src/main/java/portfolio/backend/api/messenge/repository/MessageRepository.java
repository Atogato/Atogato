package portfolio.backend.api.messenge.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import portfolio.backend.api.messenge.entity.Message;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    List<Message> findAllByReceiver(String receiverId);
    List<Message> findAllBySender(String senderId);

    @Query("SELECT m FROM Message m WHERE m.roomId = :roomId AND (m.sender = :userId OR m.receiver = :userId) ORDER BY m.createDate ASC")
    List<Message> findByRoomIdAndUser(@Param("roomId") Long roomId, @Param("userId") String userId, Sort sort);

    @Query("SELECT m.roomId FROM Message m WHERE (m.sender = :user1 AND m.receiver = :user2) OR (m.sender = :user2 AND m.receiver = :user1)")
    Optional<Long> findRoomIdByUsers(@Param("user1") String user1, @Param("user2") String user2);
    @Query("SELECT MAX(m.roomId) FROM Message m")
    Optional<Long> findMaxRoomId();

}