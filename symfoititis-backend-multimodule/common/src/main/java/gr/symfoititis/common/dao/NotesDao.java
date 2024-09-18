package gr.symfoititis.common.dao;

import gr.symfoititis.common.records.Note;

import java.util.List;

public interface NotesDao {
    /**
     *
     * Notes
     */
    List<Note> getNotes (Integer c_id);
}
