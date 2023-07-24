package portfolio.backend.api.messenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import portfolio.backend.api.messenge.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
