package portfolio.backend.api.chat.entitiy.repository;

import lombok.Data;
import lombok.NoArgsConstructor;
import portfolio.backend.authentication.api.entity.user.User;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_USER_ID")
    private User postUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatMessage> messages = new ArrayList<>();

    public ChatRoom(User postUser, User user) {
        this.postUser = postUser;
        this.user = user;
    }
}