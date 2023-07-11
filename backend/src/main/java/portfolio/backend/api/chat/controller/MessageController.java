package portfolio.backend.api.chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import portfolio.backend.api.chat.entitiy.dto.ChatMessageRequestDto;
import portfolio.backend.api.chat.entitiy.dto.ChatMessageResponseDto;
import portfolio.backend.api.chat.service.ChatMessageService;
import java.security.Principal;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MessageController {
    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/message")
    public void sendMessage(Principal principal, ChatMessageRequestDto message) {
        ChatMessageResponseDto chatMessageResponseDto = chatMessageService.messageSave(message);
        messagingTemplate.convertAndSend("/queue/chat/room/enter/"+message.getRoomId(),chatMessageResponseDto);
    }

    @GetMapping("/room/{roomId}/messages")
    @ResponseBody
    public List<ChatMessageResponseDto> messageInfo(@PathVariable Long roomId){
        return chatMessageService.messageInfoReturn(roomId);
    }
}