package portfolio.backend.api.messenger.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import portfolio.backend.api.messenger.dto.MessageDto;
import portfolio.backend.api.messenger.entity.Message;
import portfolio.backend.api.messenger.repository.MessageRepository;
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
        message.setDeletedByReceiver(false);
        message.setDeletedBySender(false);
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

        for (Message message : messages) {
            // message 에서 받은 편지함에서 삭제하지 않았으면 보낼 때 추가해서 보내줌
            if (!message.isDeletedByReceiver()) {
                messageDtos.add(MessageDto.toDto(message));
            }
        }
        return messageDtos;
    }

    // 받은 편지 삭제
    @Transactional
    public Object deleteMessageByReceiver(MessageDto messageDto, User user) {

        Message message = messageRepository.findById(messageDto.getId()).get();
        message.deleteByReceiver(); // 받은 사람에게 메시지 삭제
        if (message.isDeleted()) {
            // 받은사람과 보낸 사람 모두 삭제했으면, 데이터베이스에서 삭제요청
            messageRepository.delete(message);
            return "양쪽 모두 삭제";
        }
        return "한쪽만 삭제";
    }


    @Transactional(readOnly = true)
    public List<MessageDto> sentMessage(User user) {
        // 보낸 편지함 불러오기
        // 한 명의 유저가 받은 모든 메시지
        // 추후 JWT를 이용해서 재구현 예정
        List<Message> messages = messageRepository.findAllBySender(user);
        List<MessageDto> messageDtos = new ArrayList<>();

        for (Message message : messages) {
            // message 에서 받은 편지함에서 삭제하지 않았으면 보낼 때 추가해서 보내줌
            if (!message.isDeletedBySender()) {
                messageDtos.add(MessageDto.toDto(message));
            }
        }
        return messageDtos;
    }


    // 보낸 편지 삭제
    @Transactional
    public Object deleteMessageBySender(MessageDto messageDto, User user) {
        Message message = messageRepository.findById(messageDto.getId()).get();
        message.deleteBySender(); // 받은 사람에게 메시지 삭제
        if (message.isDeleted()) {
            // 받은사람과 보낸 사람 모두 삭제했으면, 데이터베이스에서 삭제요청
            messageRepository.delete(message);
            return "양쪽 모두 삭제";
        }
        return "한쪽만 삭제";
    }
}