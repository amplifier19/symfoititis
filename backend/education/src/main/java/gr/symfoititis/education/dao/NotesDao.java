package gr.symfoititis.education.dao;

import gr.symfoititis.education.records.Note;

import java.util.List;

public interface NotesDao {
    List<Note> getNotes (Integer c_id);
    void addNote (Note note);
    int updateNote(Note note);
    int deleteNote (Integer note_id);
}
