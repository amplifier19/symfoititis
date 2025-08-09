package gr.symfoititis.chat.controllers;

import gr.symfoititis.chat.services.BucketService;
import gr.symfoititis.chat.services.ChatService;
import gr.symfoititis.common.records.Response;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
public class BucketController {
    private final BucketService bucketService;
    private final ChatService chatService;

    public BucketController(BucketService bucketService, ChatService chatService) {
        this.bucketService = bucketService;
        this.chatService = chatService;
    }

    // TODO: Cache the url for 20 minutes
    @GetMapping("/object/{roomId}/{objectName}/generateUrl")
    ResponseEntity<Response> generateObjectPresignedUrl(
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
            @PathVariable(value="roomId", required=true)
            String roomId,
            @NotNull
            @NotBlank
            @PathVariable(value="objectName", required=true)
            String objectName
    ) {
        chatService.userBelongsToChatRoom(role, id, roomId);
        String objectPresignedUrl = bucketService.generateObjectPresignedUrl(roomId, objectName);
        return ResponseEntity.ok(new Response(200, objectPresignedUrl));
    }

    @PostMapping("/objects/{roomId}")
    ResponseEntity<Response> uploadObjects(
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
            @PathVariable(value="roomId", required=true)
            String roomId,
            @NotNull
            @NotEmpty
            @RequestParam("files") MultipartFile[] files
    ) {
        chatService.userBelongsToChatRoom(role, id, roomId);
        List<String> filenames = bucketService.uploadObjects(roomId, files);
        return ResponseEntity.ok(new Response(200, filenames));
    }
}
