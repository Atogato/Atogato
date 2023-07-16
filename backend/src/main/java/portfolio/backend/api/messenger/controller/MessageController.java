package portfolio.backend.api.messenger.controller;


import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import portfolio.backend.api.messenger.dto.MessageDto;
import portfolio.backend.api.messenger.response.Response;
import portfolio.backend.api.messenger.service.MessageService;
import portfolio.backend.authentication.api.entity.user.User;
import portfolio.backend.authentication.api.repository.user.UserRepository;
import portfolio.backend.authentication.api.service.UserService;

import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/messages")
public class MessageController {


    private final MessageService messageService;
    private final UserRepository userRepository;
    private final UserService userService;

    @ApiOperation(value = "쪽지 보내기", notes = "쪽지 보내기")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Response<?> sendMessage(@RequestBody MessageDto messageDto, Authentication authentication) {
        // 임의로 유저 정보를 넣었지만, JWT 도입하고 현재 로그인 된 유저의 정보를 넘겨줘야함
        // PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUser(principal.getUsername());
        System.out.println("메시지 1" + principal);
        System.out.println("메시지 2" + user);

        messageDto.setSenderName(user.getEmail());

        return new Response<>("성공", "쪽지를 보냈습니다.", messageService.write(messageDto));
    }


    @ApiOperation(value = "받은 편지함 읽기", notes = "받은 편지함 확인")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/received")
    public Response<?> getReceivedMessage(Authentication authentication) {
        // 임의로 유저 정보를 넣었지만, JWT 도입하고 현재 로그인 된 유저의 정보를 넘겨줘야함
//        User user = userRepository.findById(14).orElseThrow(() -> {
//            return new IllegalArgumentException("유저를 찾을 수 없습니다.");
//        });

        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUser(principal.getUsername());


        return new Response("성공", "받은 쪽지를 불러왔습니다.", messageService.receivedMessages(user));
    }

    @ApiOperation(value = "받은 쪽지 삭제하기", notes = "받은 쪽지를 삭제합니다.")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/received/{id}")
    public Response<?> deleteReceivedMessage(@PathVariable("id") Integer id, Authentication authentication) {
        // 임의로 유저 정보를 넣었지만, JWT 도입하고 현재 로그인 된 유저의 정보를 넘겨줘야함
//        User user = userRepository.findById(1).orElseThrow(() -> {
//            return new IllegalArgumentException("유저를 찾을 수 없습니다.");
//        });

        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUser(principal.getUsername());

        MessageDto messageDto = messageService.findMessageById(id);

        if (messageDto.getReceiverName().equals(user.getEmail())) {
            return new Response<>("삭제 성공", "받은 쪽지인, " + id + "번 쪽지를 삭제했습니다.", messageService.deleteMessageByReceiver(messageDto, user));
        } else {
            return new Response<>("삭제 실패", "사용자 정보가 다릅니다.", null);
        }

    }


    @ApiOperation(value = "보낸 편지함 읽기", notes = "보낸 편지함 확인")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/sent")
    public Response<?> getSentMessage(Authentication authentication) {
        // 임의로 유저 정보를 넣었지만, JWT 도입하고 현재 로그인 된 유저의 정보를 넘겨줘야함
//        User user = userRepository.findById(1).orElseThrow(() -> {
//            return new IllegalArgumentException("유저를 찾을 수 없습니다.");
//        });

        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUser(principal.getUsername());

        return new Response("성공", "보낸 쪽지를 불러왔습니다.", messageService.sentMessage(user));
    }


    @ApiOperation(value = "보낸 쪽지 삭제하기", notes = "보낸 쪽지를 삭제합니다.")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/sent/{id}")
    public Response<?> deleteSentMessage(@PathVariable("id") Integer id, Authentication authentication) {
        // 임의로 유저 정보를 넣었지만, JWT 도입하고 현재 로그인 된 유저의 정보를 넘겨줘야함
//        User user = userRepository.findById(1).orElseThrow(() -> {
//            return new IllegalArgumentException("유저를 찾을 수 없습니다.");
//        });

        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUser(principal.getUsername());

        MessageDto messageDto = messageService.findMessageById(id);

        if (messageDto.getSenderName().equals(user.getEmail())) {
            return new Response<>("삭제 성공", "보낸 쪽지인, " + id + "번 쪽지를 삭제했습니다.", messageService.deleteMessageBySender(messageDto, user));
        } else {
            return new Response<>("삭제 실패", "사용자 정보가 다릅니다.", null);
        }
    }


}