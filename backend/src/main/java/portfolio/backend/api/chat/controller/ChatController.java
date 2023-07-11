package portfolio.backend.api.chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import portfolio.backend.api.chat.entitiy.repository.ChatRoom;
import portfolio.backend.api.chat.entitiy.repository.ChatRoomRepository;
import portfolio.backend.api.chat.service.ChatRoomService;
import portfolio.backend.authentication.api.entity.user.User;
import portfolio.backend.authentication.api.repository.user.UserRepository;
import portfolio.backend.authentication.api.service.UserService;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Slf4j
@Controller
public class ChatController {

    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomService chatService;
    private final UserService userService;

    @PostMapping("/chat/creating")
    public String createChatRoom(@RequestParam("postUser") Long findUser, @RequestParam("user") Long sendUser){
        //Long loginUser = (Long) session.getAttribute("loginId");
        Long loginUser = userRepository.findById(sendUser).get().getUserSeq();
        //org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //User user = userService.getUser(principal.getUsername());

        if(findUser.equals(loginUser)) return "redirect:/mypageChat";
        else {
            ChatRoom chatRoom = chatRoomRepository.findByUserAndPostUser(loginUser, findUser).orElse(null);
            ChatRoom chatRoom1 = chatRoomRepository.findByUserAndPostUser(findUser, loginUser).orElse(null);
            if(chatRoom!=null) return "redirect:/chat/room/enter/" + chatRoom.getId();
            if(chatRoom1!=null) return "redirect:/chat/room/enter/" + chatRoom1.getId();
            Long[] chatMember = {findUser, loginUser};
            Long chatRoomId = chatService.createChat(chatMember);
            return "redirect:/chat/room/enter/" + chatRoomId;
        }
    }

    @GetMapping("/chat/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable Long roomId){
        return chatService.roomInfoReturn(roomId);
    }

    @GetMapping("/chat/room/user/{userId}")
    @ResponseBody
    public User sendUser(@PathVariable Long userId){
        return userRepository.findById(userId).orElseThrow(null);
    }

    @GetMapping("/chat/room/enter/{roomId}")
    public String enterChatroom(@PathVariable Long roomId, @RequestParam("user") Long sendUser,Model model) {
        try {
            //Long loginUserId = (Long) session.getAttribute("loginId");
            Long loginUserId = userRepository.findById(sendUser).get().getUserSeq();
            model.addAttribute("nowSender", loginUserId);
            model.addAttribute("roomInfo", roomId);
            return "chat/chat";
        } catch (Exception e) {
            log.error("NOT FIND CHATROOM : {}",e);
            return "redirect:/post/home";
        }
    }
}
