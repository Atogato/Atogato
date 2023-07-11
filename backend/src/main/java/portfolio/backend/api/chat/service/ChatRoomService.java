package portfolio.backend.api.chat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import portfolio.backend.api.chat.entitiy.repository.ChatRoom;
import portfolio.backend.api.chat.entitiy.repository.ChatRoomRepository;
import portfolio.backend.authentication.api.entity.user.User;
import portfolio.backend.authentication.api.repository.user.UserRepository;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatRoomService {
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public Long createChat(Long[] users) {
        List<User> chatMember = userRepository.findAllById(Arrays.asList(users));
        ChatRoom createdChatRoom = new ChatRoom(chatMember.get(0), chatMember.get(1));
        chatRoomRepository.save(createdChatRoom);
        return createdChatRoom.getId();
    }

    @Transactional(readOnly = true)
    public ChatRoom roomInfoReturn(Long roomId) {
        return chatRoomRepository.findById(roomId).orElse(null);
    }

}
