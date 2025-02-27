package gr.symfoititis.chat.controllers;

import gr.symfoititis.chat.entities.ChatMessage;
import gr.symfoititis.chat.services.ChatService;
import gr.symfoititis.common.exceptions.BadRequestException;
import gr.symfoititis.common.exceptions.ForbiddenException;
import gr.symfoititis.common.records.Response;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static gr.symfoititis.common.utils.RoleValidation.isStudentOrTeacher;

@Validated
@RestController
public class ChatController {
    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;
    public ChatController (ChatService chatService, SimpMessagingTemplate messagingTemplate) {
        this.chatService = chatService;
        this.messagingTemplate = messagingTemplate;
    }
    @MessageMapping("/send")
    void sendMessage(
            @Valid @Payload ChatMessage chatMessage
    ) {
        chatService.addMessage(chatMessage);
        messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipient_id(), "/queue/messages",
                chatMessage
        );
    }
    @GetMapping("/messages/{c_id}/{participant_id}")
    ResponseEntity<Response> getMessages(
            @NotNull
            @NotBlank
            @RequestHeader("X-Role")
            String role,
            @NotNull
            @NotBlank
            @RequestHeader("X-User-Id")
            String id,
            @NotNull
            @NotBlank
            @RequestHeader("X-Department-Id")
            String dep_id,
            @Positive
            @PathVariable(value="c_id", required=true)
            int c_id,
            @NotNull
            @NotBlank
            @PathVariable(value="participant_id", required=true)
            String participant_id,
            @PositiveOrZero
            @RequestParam(name = "offset", required = false)
            Integer offset
    ) {
        isStudentOrTeacher(role);
        try {
            @Positive
            int departmentId = Integer.parseInt(dep_id);
            String input;
            String roomId = switch (role) {
                case "student" -> {
                    input = departmentId + c_id + participant_id + id;
                    yield UUID.nameUUIDFromBytes(input.getBytes()).toString();
                }
                case "teacher" -> {
                    input = departmentId + c_id + id + participant_id;
                    yield UUID.nameUUIDFromBytes(input.getBytes()).toString();
                }
                default -> throw new ForbiddenException("Invalid role");
            };
            offset = offset == null ? 0 : offset;
            List<ChatMessage> chatMessages = chatService.getMessages(roomId, offset);
            return ResponseEntity.ok(new Response(200, chatMessages));
        } catch (NumberFormatException ex) {
            throw new BadRequestException("Department id cannot be parsed to integer");
        }
    }
}

