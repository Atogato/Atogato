package portfolio.backend.api.messenge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import portfolio.backend.api.messenge.dto.MessageDto;
import portfolio.backend.api.messenge.entity.Message;
import portfolio.backend.api.messenge.exception.UnauthorizedAccessException;
import portfolio.backend.api.messenge.repository.MessageRepository;
import portfolio.backend.api.messenge.repository.RoomRepository;
import portfolio.backend.authentication.api.entity.user.User;
import portfolio.backend.authentication.api.repository.user.UserRepository;

import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class MessageService {

    private final RoomIdGenerator roomIdGenerator;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    @Transactional
    public MessageDto write(MessageDto messageDto) {
        String receiverId = messageDto.getReceiverName();
        String senderId = messageDto.getSenderName();

        User sender = userRepository.findByUserId(senderId);
        if (sender == null) {
            throw new UnauthorizedAccessException("발신자 유저로 존재 하지 않습니다");
        }

        User receiver = userRepository.findByUserId(receiverId);

        if (receiver == null) {
            throw new UnauthorizedAccessException("송신자 유저로 존재 하지 않습니다");
        }

        Optional<Long> roomIdOptional = messageRepository.findRoomIdByUsers(senderId, receiverId);

        Long roomId = roomIdOptional.orElseGet(() -> roomIdGenerator.generateRoomId(senderId, receiverId));

        Message message = new Message();
        message.setReceiver(receiverId);
        message.setSender(senderId);
        message.setContent(messageDto.getContent());
        message.setCreateDate(LocalDateTime.now());
        message.setRoomId(roomId);

        messageRepository.save(message);
        return MessageDto.toDto(message);
    }

    @Transactional(readOnly = true)
    public List<MessageDto> getMessagesByRoomIdAndUser(Long roomId, String userId) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createDate");
        List<Message> messages = messageRepository.findByRoomIdAndUser(roomId, userId, sort);
        if (messages.isEmpty()) {
            throw new UnauthorizedAccessException("유저는 특정 방에 포함되어 있지 않습니다");
        }
        List<MessageDto> messageDtos = new ArrayList<>();
        for (Message message : messages) {
            messageDtos.add(MessageDto.toDto(message));
        }
        return messageDtos;
    }
}

