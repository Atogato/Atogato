package portfolio.backend.api.chat.entitiy.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ChatMessageRequestDto {
    private String message;
    private String sender;
    private String roomId;

    public ChatMessageRequestDto(String message, String sender, String roomId) {
        this.message = message;
        this.sender = sender;
        this.roomId = roomId;
    }
}
