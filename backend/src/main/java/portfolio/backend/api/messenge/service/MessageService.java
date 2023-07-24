package portfolio.backend.api.messenge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import portfolio.backend.api.messenge.dto.MessageDto;
import portfolio.backend.api.messenge.entity.Message;
import portfolio.backend.api.messenge.entity.Room;
import portfolio.backend.api.messenge.exception.UnauthorizedAccessException;
import portfolio.backend.api.messenge.repository.MessageRepository;
import portfolio.backend.api.messenge.repository.RoomRepository;
import portfolio.backend.authentication.api.entity.user.User;
import portfolio.backend.authentication.api.repository.user.UserRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MessageService {

//    private final RoomIdGenerator roomIdGenerator = new RoomIdGenerator();

    private final RoomIdGenerator roomIdGenerator;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public MessageService(RoomIdGenerator roomIdGenerator, RoomRepository roomRepository, MessageRepository messageRepository, UserRepository userRepository) {
        this.roomIdGenerator = roomIdGenerator;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;

    }

    @Transactional
    public MessageDto write(MessageDto messageDto) {
        String receiverId = messageDto.getReceiverName();
        String senderId = messageDto.getSenderName();

        Optional<Long> roomIdOptional = messageRepository.findRoomIdByUsers(senderId, receiverId);

        Long roomId;
        if(roomIdOptional.isPresent()) {
            roomId = roomIdOptional.get();
            System.out.println("로그 yes: " + roomId);

        } else {
            roomId = roomIdGenerator.generateRoomId(senderId, receiverId);
        }

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
            throw new UnauthorizedAccessException("The user is not part of the specified room");
        }
        List<MessageDto> messageDtos = new ArrayList<>();
        for (Message message : messages) {
            messageDtos.add(MessageDto.toDto(message));
        }
        return messageDtos;
    }
}
