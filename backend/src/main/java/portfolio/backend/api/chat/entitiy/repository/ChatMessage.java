package portfolio.backend.api.chat.entitiy.repository;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import portfolio.backend.authentication.api.entity.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    @Column
    private String message;

    @Column
    private LocalDateTime createdTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User sender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CHATROOM_ID", referencedColumnName = "id")
    private ChatRoom chatRoom;

    public ChatMessage(String message, User sender, ChatRoom chatRoom) {
        this.message = message;
        this.sender = sender;
        this.chatRoom = chatRoom;
        this.createdTime = LocalDateTime.now();
        chatRoom.getMessages().add(this);
    }
}