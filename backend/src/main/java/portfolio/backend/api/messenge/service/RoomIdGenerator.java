package portfolio.backend.api.messenge.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import portfolio.backend.api.messenge.entity.Room;
import portfolio.backend.api.messenge.repository.MessageRepository;
import portfolio.backend.api.messenge.repository.RoomRepository;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class RoomIdGenerator {
    private Map<Set<String>, Long> roomMap;
    private long nextRoomId;
    private RoomRepository roomRepository;
    private final MessageRepository messageRepository;

    public RoomIdGenerator(MessageRepository messageRepository, RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
        this.messageRepository = messageRepository;
    }

    // 서버가 재시작을 할때 in-memory를 사용하는 대신에 roomId중 최대값을 가져와 다시 시작하겠금 구현
    @PostConstruct
    public void initialize() {
        roomMap = new HashMap<>();
        roomRepository.findAll().forEach(room -> {
            List<String> idList = Arrays.asList(room.getUser1Id(), room.getUser2Id());
            Collections.sort(idList);
            Set<String> idSet = new HashSet<>(idList);
            roomMap.put(idSet, room.getRoomId());
        });

        // max를 찾는다
        Optional<Long> maxRoomIdOptional = messageRepository.findMaxRoomId();
        nextRoomId = maxRoomIdOptional.map(id -> id + 1).orElse(1L);
    }

    // room 테이블은 auto-incrmenet 충돌 방지를 위해 타임스탬프로 찍는다
    public Long generateId() {
        return System.currentTimeMillis();
    }

    @Transactional
    public Long generateRoomId(String senderId, String receiverId) {
        List<String> idList = Arrays.asList(senderId, receiverId);
        Collections.sort(idList);
        Set<String> idSet = new HashSet<>(idList);

        if (roomMap.containsKey(idSet)) {
            return roomMap.get(idSet);
        }

        long newRoomId = nextRoomId++;
        roomMap.put(idSet, newRoomId);

        Room room = new Room();
        room.setId(generateId());
        room.setRoomId(newRoomId);
        room.setUser1Id(senderId);
        room.setUser2Id(receiverId);
        roomRepository.save(room);

        return room.getRoomId();
    }
}