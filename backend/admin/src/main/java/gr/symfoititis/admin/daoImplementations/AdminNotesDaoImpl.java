package gr.symfoititis.admin.daoImplementations;

import gr.symfoititis.admin.dao.NotesDao;
import gr.symfoititis.common.records.Note;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class NotesDaoImpl implements NotesDao {
    private final JdbcTemplate jdbcTemplate;
    public NotesDaoImpl (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    /**
     *
     * Notes
     */

    @Override
    public int addNote(Note note) {
        String sql = "INSERT INTO notes (c_id, type, note_display_name, note_filename) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, note.c_id(), note.type(), note.note_display_name(), note.note_filename());
    }

    @Override
    public int updateNote(Note note) {
        String sql = "UPDATE notes SET c_id = ?, type = ?, note_display_name = ?, note_filename = ? WHERE note_id = ?";
        return jdbcTemplate.update(sql, note.c_id(), note.type(), note.note_display_name(), note.note_filename(), note.note_id());
    }

    @Override
    public int deleteNote(Integer note_id) {
        String sql = "DELETE FROM notes WHERE note_id = ?";
        return jdbcTemplate.update(sql, note_id);
    }
}
