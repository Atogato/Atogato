package portfolio.backend.api.messenger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import portfolio.backend.api.messenger.entity.Message;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private int id;
    private String title;
    private String content;
    private String senderName;
    private String receiverName;

    public static MessageDto toDto(Message message) {
        return new MessageDto(
                message.getId(),
                message.getTitle(),
                message.getContent(),
                message.getSender().getEmail(),
                message.getReceiver().getEmail()
        );
    }
}