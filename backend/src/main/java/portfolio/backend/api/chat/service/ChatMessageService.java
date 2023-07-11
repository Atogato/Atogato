package portfolio.backend.api.chat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import portfolio.backend.api.chat.entitiy.dto.ChatMessageRequestDto;
import portfolio.backend.api.chat.entitiy.dto.ChatMessageResponseDto;
import portfolio.backend.api.chat.entitiy.repository.ChatMessage;
import portfolio.backend.api.chat.entitiy.repository.ChatMessageRepository;
import portfolio.backend.api.chat.entitiy.repository.ChatRoom;
import portfolio.backend.api.chat.entitiy.repository.ChatRoomRepository;
import portfolio.backend.authentication.api.entity.user.User;
import portfolio.backend.authentication.api.repository.user.UserRepository;
import portfolio.backend.authentication.api.service.UserService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatMessageService {
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    //private final UserService userService;
    private final ChatMessageRepository chatMessageRepository;

    @Transactional
    public ChatMessageResponseDto messageSave(ChatMessageRequestDto messageDto){
        ChatRoom chatRoom = chatRoomRepository.findById(Long.parseLong(messageDto.getRoomId())).orElse(null);
        User sendUser = userRepository.findById(Long.parseLong(messageDto.getSender())).orElse(null);
        String username = sendUser.getUsername();
        if (chatRoom != null && sendUser != null) {
            ChatMessage message = new ChatMessage(messageDto.getMessage(), sendUser, chatRoom);
            chatMessageRepository.save(message);
        }
        return new ChatMessageResponseDto(messageDto.getSender(), messageDto.getMessage(), messageDto.getRoomId(), username);
    }

    @Transactional(readOnly = true)
    public List<ChatMessageResponseDto> messageInfoReturn(Long roomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElse(null);
        List<ChatMessage> messages = chatRoom.getMessages();
        List<ChatMessageResponseDto> result = new ArrayList<>();
        for (ChatMessage c : messages) {
            result.add(new ChatMessageResponseDto(String.valueOf(c.getSender().getUserId()), c.getMessage(), String.valueOf(roomId),c.getSender().getUsername()));
        }
        Collections.reverse(result);
        return result;
    }
}