package gr.symfoititis.admin.dao;


import gr.symfoititis.common.records.Note;

public interface NotesDao {

    /**
     *
     * Notes
     */
    int addNote (Note note);
    int updateNote(Note note);
    int deleteNote (Integer note_id);
}
