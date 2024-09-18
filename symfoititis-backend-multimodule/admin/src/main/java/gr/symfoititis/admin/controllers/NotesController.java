package gr.symfoititis.admin.controllers;

import gr.symfoititis.admin.services.AdminNotesService;
import gr.symfoititis.common.records.Note;
import gr.symfoititis.common.records.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/admin")
public class NotesController {
private final AdminNotesService adminNotesService;
private final gr.symfoititis.common.services.NotesService commonNotesService;

    public NotesController(AdminNotesService adminNotesService, gr.symfoititis.common.services.NotesService commonNotesService) {
        this.adminNotesService = adminNotesService;
        this.commonNotesService = commonNotesService;
    }

    /**
     *
     * Notes
     */
    @GetMapping("/notes/{c_id}")
    ResponseEntity<Response> getNotes (@PathVariable(value="c_id", required=true) Integer c_id) {
        List<Note> notes = commonNotesService.getNotes(c_id);
        return ResponseEntity.ok(new Response(200, notes));
    }
    @PostMapping("/note")
    ResponseEntity<Response> addNote (@RequestBody Note note) {
        adminNotesService.addNote (note);
        String message = "Successfully added note";
        return ResponseEntity.ok(new Response (200, message));    }
    @PutMapping("/note")
    ResponseEntity<Response> updateNote (@RequestBody Note note) {
        adminNotesService.updateNote(note);
        String message = String.format("Successfully updated note with id: %d", note.note_id());
        return ResponseEntity.ok(new Response(200, message));
    }
    @DeleteMapping("/note/{note_id}")
    ResponseEntity<Response> deleteNote (@PathVariable(value="note_id", required=true) Integer note_id) {
        adminNotesService.deleteNote(note_id);
        String message = String.format("Successfully deleted note with id: %d", note_id);
        return ResponseEntity.ok(new Response (200, message));
    }
}
