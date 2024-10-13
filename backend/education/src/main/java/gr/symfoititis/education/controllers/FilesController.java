package gr.symfoititis.education.controllers;

import gr.symfoititis.education.services.FilesService;
import gr.symfoititis.common.records.Response;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static gr.symfoititis.common.utils.RoleValidation.isAdmin;
import static gr.symfoititis.common.utils.RoleValidation.isAnyone;

@RestController
public class FilesController {
    private final FilesService filesService;

    public FilesController(FilesService filesService) {
        this.filesService = filesService;
    }

    @GetMapping("/files/{c_id}")
    ResponseEntity<Response> getFiles (
            @RequestHeader("X-Role")
            @NotNull(message = "Role cannot be null")
            @NotBlank(message = "Role cannot be blank")
            String role,
            @PathVariable(value="c_id", required=true)
            @Positive(message = "Course is must be positive")
            int c_id
    ) {
        isAnyone(role);
        List<String> files = filesService.getFiles (c_id);
        return ResponseEntity.ok(new Response(200, files));
    }

    @PostMapping("/files/upload/{c_id}")
    ResponseEntity<Response> uploadFiles (
            @RequestHeader("X-Role")
            @NotNull(message = "Role cannot be null")
            @NotBlank(message = "Role cannot be blank")
            String role,
            @PathVariable(value="c_id", required=true)
            @Positive(message = "Course id must be positive")
            int c_id,
            @RequestParam("files") MultipartFile[] files
    ) {
        isAdmin(role);
        List<String> filenames = filesService.uploadMany(c_id, files);
        return ResponseEntity.ok(new Response (200, filenames));
    }

    @DeleteMapping("/files/{c_id}/{filename}")
    ResponseEntity<Response> deleteFile (
            @RequestHeader("X-Role")
            @NotNull(message = "Role cannot be null")
            @NotBlank(message = "Role cannot be blank")
            String role,
            @PathVariable(value="c_id", required=true)
            @Positive(message = "Course id must be positive")
            int c_id,
            @NotNull(message = "File name cannot be null")
            @NotBlank(message = "File name cannot be blank")
            @PathVariable(value="filename", required=true)
            String filename
    ) {
        isAdmin(role);
        filesService.deleteFile (c_id, filename);
        String message = String.format("Successfully deleted %s", filename);
        return ResponseEntity.ok(new Response(200, message));
    }
}