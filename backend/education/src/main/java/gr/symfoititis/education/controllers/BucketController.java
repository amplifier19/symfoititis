package gr.symfoititis.education.controllers;

import gr.symfoititis.common.records.Response;
import gr.symfoititis.education.services.BucketService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static gr.symfoititis.common.utils.RoleValidation.*;

import java.util.List;

@RestController
public class BucketController {

    private final BucketService bucketService;

    public BucketController(BucketService bucketService) {
        this.bucketService = bucketService;
    }

    @GetMapping("/objects/{c_id}")
    ResponseEntity<Response> listObjectsByCourseId(
            @NotNull
            @NotBlank
            @RequestHeader("X-Role")
            String role,
            @Positive
            @PathVariable(value="c_id", required=true)
            int c_id
    ) {
        // TODO: check if course belongs to the user's department
        isAnyone(role);
        List<String> objects = bucketService.listObjectsByCourseId(c_id);
        return ResponseEntity.ok(new Response(200, objects));
    }
    
    // TODO: Cache the url for 20 minutes
    @GetMapping("/object/{c_id}/{objectName}/generateUrl")
    ResponseEntity<Response> generateObjectPresignedUrl(
            @NotNull
            @NotBlank
            @RequestHeader("X-Role")
            String role,
            @Positive
            @PathVariable(value="c_id", required=true)
            int c_id,
            @NotNull
            @NotBlank
            @PathVariable(value="objectName", required=true)
            String objectName
    ) {
        // TODO: check if course belongs to the user's department
        isAnyone(role);
        String url = bucketService.generateObjectPresignedUrl(c_id, objectName);
        return ResponseEntity.ok(new Response(200, url));
    }

    @PostMapping("/objects/{c_id}")
    ResponseEntity<Response> uploadObjects (
            @NotNull
            @NotBlank
            @RequestHeader("X-Role")
            String role,
            @Positive
            @PathVariable(value="c_id", required=true)
            int c_id,
            @NotNull
            @NotEmpty
            @RequestParam("files") MultipartFile[] files
    ) {
        isAdmin(role);
        List<String> filenames = bucketService.uploadObjects(c_id, files);
        String message = String.format("Successfully uploaded %d files", filenames.size());
        return ResponseEntity.ok(new Response (200, message));
    }

    @DeleteMapping("/object/{c_id}/{objectName}")
    ResponseEntity<Response> deleteObject (
            @NotNull
            @NotBlank
            @RequestHeader("X-Role")
            String role,
            @Positive
            @PathVariable(value="c_id", required=true)
            int c_id,
            @NotNull
            @NotBlank
            @PathVariable(value="objectName", required=true)
            String objectName
    ) {
        isAdmin(role);
        bucketService.deleteObject(c_id, objectName);
        String message = String.format("Successfully deleted %s", objectName);
        return ResponseEntity.ok(new Response(200, message));
    }

}
