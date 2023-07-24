package portfolio.backend.api.messenge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import portfolio.backend.api.messenge.entity.Message;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {

    private Long id;
    private String content;
    private String senderName;
    private String receiverName;
    private Long roomId;
    private LocalDateTime createDate;

    public static MessageDto toDto(Message message) {
        return new MessageDto(
                message.getId(),
                message.getContent(),
                message.getSender(),
                message.getReceiver(),
                message.getRoomId(),
                message.getCreateDate()
        );
    }
}