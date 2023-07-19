package portfolio.backend.api.messenge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import portfolio.backend.api.messenge.dto.MessageDto;
import portfolio.backend.api.messenge.entity.Message;
import portfolio.backend.api.messenge.repository.MessageRepository;
import portfolio.backend.authentication.api.entity.user.User;
import portfolio.backend.authentication.api.repository.user.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MessageService {
    
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;


    @Transactional
    public MessageDto write(MessageDto messageDto) {
        User receiver = userRepository.findByEmail(messageDto.getReceiverName());
        User sender = userRepository.findByEmail(messageDto.getSenderName());

        Message message = new Message();
        message.setReceiver(receiver);
        message.setSender(sender);
        message.setContent(messageDto.getContent());
        message.setCreateDate(LocalDateTime.now());
        messageRepository.save(message);

        return MessageDto.toDto(message);
    }

    @Transactional(readOnly = true)
    public MessageDto findMessageById(int id) {
        Message message = messageRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("메시지를 찾을 수 없습니다.");
        });

        return MessageDto.toDto(message);
    }

    @Transactional(readOnly = true)
    public List<MessageDto> receivedMessages(User user) {
        // 받은 편지함 불러오기
        // 한 명의 유저가 받은 모든 메시지
        // 추후 JWT를 이용해서 재구현 예정
        List<Message> messages = messageRepository.findAllByReceiver(user);
        List<MessageDto> messageDtos = new ArrayList<>();

        return messageDtos;
    }


    @Transactional(readOnly = true)
    public List<MessageDto> sentMessage(User user) {
        // 보낸 편지함 불러오기
        // 한 명의 유저가 받은 모든 메시지
        // 추후 JWT를 이용해서 재구현 예정
        List<Message> messages = messageRepository.findAllBySender(user);
        List<MessageDto> messageDtos = new ArrayList<>();

        return messageDtos;
    }


}