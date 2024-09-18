package gr.symfoititis.student.controller;

import gr.symfoititis.common.records.Note;
import gr.symfoititis.common.records.Response;
import gr.symfoititis.common.services.NotesService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/student")
public class NotesController {
    private final NotesService notesService;
    public NotesController(NotesService notesService) {
        this.notesService = notesService;
    }

    /**
     *
     * Notes
     */
    @GetMapping("/notes/{c_id}")
    ResponseEntity<Response> getNotes (@PathVariable(value="c_id", required=true) Integer c_id) {
        List<Note> notes = notesService.getNotes(c_id);
        String message = String.format("Successfully fetched %d notes", notes.size());
        return ResponseEntity.ok(new Response(200, notes));
    }
}
