package portfolio.backend.api.chat.entitiy.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatMessageResponseDto {
    private String sender;
    private String message;
    private String roomId;
    private String senderName;

    public ChatMessageResponseDto(String sender, String message, String roomId, String senderName) {
        this.sender = sender;
        this.message = message;
        this.roomId = roomId;
        this.senderName = senderName;
    }
}