//package portfolio.backend.api.chat.controller;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import portfolio.backend.api.chat.entitiy.dto.MyPageDto;
//import portfolio.backend.api.chat.entitiy.repository.ChatRoom;
//import portfolio.backend.api.chat.entitiy.repository.ChatRoomRepository;
//import portfolio.backend.authentication.api.repository.user.UserRepository;
//
//import javax.servlet.http.HttpSession;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//
//@Slf4j
//@RequiredArgsConstructor
//@Controller
//public class MyPageController {
//
//    private final UserRepository userRepository;
//    private final ChatRoomRepository chatRoomRepository;
//    @GetMapping("/mypageChat")
//    public String mypageChatList(Model model, @RequestParam("postUser") Long findUser, @RequestParam("user") Long sendUser) {
//        //Long userId = (Long) httpSession.getAttribute("loginId");
//        Long userId = userRepository.findById(sendUser).get().getUserSeq();
//
//        List<ChatRoom> findChatRoom = chatRoomRepository.findAllByUserOrPostUser(userId,userId);
//
//        List<MyPageDto> responseDto = new ArrayList<>();
//        for (ChatRoom room : findChatRoom) {
//            if(room.getMessages().size()==0)continue;
//            responseDto.add(new MyPageDto(room.getMessages().get(room.getMessages().size()-1).getCreatedTime(),
//                    room.getMessages().get(room.getMessages().size()-1).getMessage(), room.getId()));
//            log.info("{} {} {}",responseDto.get(0).getCreatedTime(),responseDto.get(0).getMessage(),responseDto.get(0).getRoomId());
//        }
//
//        Collections.sort(responseDto, Comparator.comparing(MyPageDto::getCreatedTime));
//        Collections.reverse(responseDto);
//        model.addAttribute("chatList", responseDto);
//
//        return "chat/chatList";
//    }
//}
