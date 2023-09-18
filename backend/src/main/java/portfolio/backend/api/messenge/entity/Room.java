package portfolio.backend.api.messenge.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Room {

    @Id
    private Long id;
    private Long roomId;
    private String user1Id;
    private String user2Id;
}
