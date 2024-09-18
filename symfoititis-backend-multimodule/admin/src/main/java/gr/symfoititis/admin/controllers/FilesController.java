package gr.symfoititis.admin.controllers;

import gr.symfoititis.admin.services.FilesService;
import gr.symfoititis.common.records.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/rest/admin")
public class FilesController {
    private final FilesService filesService;

    public FilesController(FilesService filesService) {
        this.filesService = filesService;
    }

    /**
     *
     * Files
     */
    @GetMapping("/files/{c_id}")
    ResponseEntity<Response> getFiles (@PathVariable(value="c_id", required=true) Integer c_id) {
        List<String> files = filesService.getFiles (c_id);
        String message = String.format("Successfully fetched %d notes", files.size());
        return ResponseEntity.ok(new Response(200, files));
    }
    @PostMapping("/files/upload/{c_id}")
    ResponseEntity<Response> uploadFiles (@PathVariable(value="c_id", required=true) Integer c_id, @RequestParam("files") MultipartFile[] files) {
        List<String> filenames = filesService.uploadMany(c_id, files);
        String message = String.format ("Successfully added %d files", filenames.size());
        return ResponseEntity.ok(new Response (200, filenames));
    }
    @DeleteMapping("/files/{c_id}/{filename}")
    ResponseEntity<Response> deleteFile (@PathVariable(value="c_id",required=true) Integer c_id, @PathVariable(value="filename", required=true) String filename) {
        filesService.deleteFile (c_id, filename);
        String message = String.format("Successfully deleted %s", filename);
        return ResponseEntity.ok(new Response(200, filename));
    }
}
