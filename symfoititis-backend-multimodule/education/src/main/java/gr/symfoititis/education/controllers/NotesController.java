package gr.symfoititis.education.controllers;

import gr.symfoititis.common.records.Response;
import gr.symfoititis.education.records.Note;
import gr.symfoititis.education.services.NotesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static gr.symfoititis.common.utils.RoleValidation.isAdmin;
import static gr.symfoititis.common.utils.RoleValidation.isAnyone;

@RestController
public class NotesController {
    private final NotesService notesService;

    public NotesController(NotesService notesService) {
        this.notesService = notesService;
    }

    @GetMapping("/notes/{c_id}")
    ResponseEntity<Response> getNotes (
            @PathVariable(value="c_id", required=true) Integer c_id,
            @RequestHeader("X-Role") String role
    ) {
        isAnyone(role);
        List<Note> notes = notesService.getNotes(c_id);
        return ResponseEntity.ok(new Response(200, notes));
    }

    @PostMapping("/note")
    ResponseEntity<Response> addNote (
            @RequestHeader("X-Role") String role,
            @RequestBody Note note
    ) {
        isAdmin(role);
        notesService.addNote (note);
        String message = "Successfully added note";
        return ResponseEntity.ok(new Response (200, message));
    }

    @PutMapping("/note")
    ResponseEntity<Response> updateNote (
            @RequestHeader("X-Role") String role,
            @RequestBody Note note
    ) {
        isAdmin(role);
        notesService.updateNote(note);
        String message = String.format("Successfully updated note with id: %d", note.note_id());
        return ResponseEntity.ok(new Response(200, message));
    }

    @DeleteMapping("/note/{note_id}")
    ResponseEntity<Response> deleteNote (
            @PathVariable(value="note_id", required=true) Integer note_id,
            @RequestHeader("X-Role") String role
    ) {
        isAdmin(role);
        notesService.deleteNote(note_id);
        String message = String.format("Successfully deleted note with id: %d", note_id);
        return ResponseEntity.ok(new Response (200, message));
    }
}
