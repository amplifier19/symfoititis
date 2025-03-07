package gr.symfoititis.education.controllers;

import gr.symfoititis.education.services.FilesService;
import gr.symfoititis.common.records.Response;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
            @NotNull
            @NotBlank
            @RequestHeader("X-Role")
            String role,
            @Positive
            @PathVariable(value="c_id", required=true)
            int c_id
    ) {
        isAnyone(role);
        List<String> files = filesService.getFiles (c_id);
        return ResponseEntity.ok(new Response(200, files));
    }

    @PostMapping("/files/upload/{c_id}")
    ResponseEntity<Response> uploadFiles (
            @NotNull
            @NotBlank
            @RequestHeader("X-Role")
            String role,
            @Positive
            @PathVariable(value="c_id", required=true)
            int c_id,
            @RequestParam("files") MultipartFile[] files
    ) {
        isAdmin(role);
        List<String> filenames = filesService.uploadMany(c_id, files);
        return ResponseEntity.ok(new Response (200, filenames));
    }

    @DeleteMapping("/files/{c_id}/{filename}")
    ResponseEntity<Response> deleteFile (
            @NotNull
            @NotBlank
            @RequestHeader("X-Role")
            String role,
            @Positive
            @PathVariable(value="c_id", required=true)
            int c_id,
            @NotNull
            @NotBlank
            @PathVariable(value="filename", required=true)
            String filename
    ) {
        isAdmin(role);
        filesService.deleteFile (c_id, filename);
        String message = String.format("Successfully deleted %s", filename);
        return ResponseEntity.ok(new Response(200, message));
    }

//
    @GetMapping("/objects/{c_id}")
    ResponseEntity<Response> listObjectsByCourseId(
            @Positive
            @PathVariable(value="c_id", required=true)
            int c_id
    ) {
       List<String> objects = filesService.listObjectsByCourseId(c_id);
       return ResponseEntity.ok(new Response(200, objects));
    }

    @GetMapping("/object/generateUrl")
    ResponseEntity<Response> generateObjectPresignedUrl() {
        String url = filesService.generateObjectPresignedUrl();
        return ResponseEntity.ok(new Response(200, url));
    }

    @PostMapping("/objects/{c_id}")
    ResponseEntity<Response> uploadObjects (
            @Positive
            @PathVariable(value="c_id", required=true)
            int c_id,
            @NotNull
            @NotEmpty
            @RequestParam("files") MultipartFile[] files
    ) {
        List<String> filenames = filesService.uploadObjects(c_id, files);
        return ResponseEntity.ok(new Response (200, filenames));
    }

    @DeleteMapping("/object/{c_id}/{objectName}")
    ResponseEntity<Response> deleteFile (
            @Positive
            @PathVariable(value="c_id", required=true)
            int c_id,
            @NotNull
            @NotBlank
            @PathVariable(value="objectName", required=true)
            String objectName
    ) {
        filesService.deleteObject(c_id, objectName);
        String message = String.format("Successfully deleted %s", objectName);
        return ResponseEntity.ok(new Response(200, message));
    }
}