package portfolio.backend.api.messenge.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import portfolio.backend.api.messenge.dto.MessageDto;
import portfolio.backend.api.messenge.exception.UnauthorizedAccessException;
import portfolio.backend.api.messenge.response.Response;
import portfolio.backend.api.messenge.service.MessageService;
import portfolio.backend.authentication.api.entity.user.User;
import portfolio.backend.authentication.api.service.UserContextService;
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Api(tags = {"Messenger"})
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;
    private final UserContextService userContextService;


    @ApiOperation(value = "쪽지 보내기", notes = "쪽지 보내기")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Response<?> sendMessage(@RequestBody String content, @RequestParam String receiverId, @ApiIgnore Authentication authentication) {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userContextService.getCurrentUser();

        MessageDto messageDto = new MessageDto();
        messageDto.setSenderName(user.getUserId());
        messageDto.setReceiverName(receiverId);
        messageDto.setContent(content);
        messageDto.setCreateDate(LocalDateTime.now());

        return new Response<>("성공", "쪽지를 보냈습니다.", messageService.write(messageDto));
    }

    @ApiOperation(value = "메시지 읽기", notes = "특정 방 ID를 가진 모든 메시지 읽기")
    @GetMapping("/{roomId}")
    public Response<?> getMessagesByRoomId(@PathVariable Long roomId, @ApiIgnore Authentication authentication) {
        User user = userContextService.getCurrentUser();

        List<MessageDto> messages = messageService.getMessagesByRoomIdAndUser(roomId, user.getUserId());
        return new Response<>("성공", "메시지를 불러왔습니다.", messages);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<?> handleUnauthorizedAccess(UnauthorizedAccessException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Error(e.getMessage()));
    }
}