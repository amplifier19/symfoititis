package gr.symfoititis.education.daoImplementations;

import gr.symfoititis.education.rowMappers.NotesRowMapper;
import gr.symfoititis.education.dao.NotesDao;
import gr.symfoititis.education.records.Note;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NotesDaoImpl implements NotesDao {
    protected final JdbcTemplate jdbcTemplate;

    public NotesDaoImpl (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Note> getNotes(Integer c_id) {
        String sql = "SELECT * FROM notes n WHERE n.c_id = ? ORDER BY n.note_display_name";
        return jdbcTemplate.query(sql, new NotesRowMapper(), c_id);
    }

    @Override
    public void addNote(Note note) {
        String sql = "INSERT INTO notes (c_id, type, note_display_name, note_filename) VALUES (?, ?::note_type_enum, ?, ?)";
        jdbcTemplate.update(sql, note.c_id(), note.type(), note.note_display_name(), note.note_filename());
    }

    @Override
    public int updateNote(Note note) {
        String sql = "UPDATE notes SET c_id = ?, type = ?::note_type_enum, note_display_name = ?, note_filename = ? WHERE note_id = ?";
        return jdbcTemplate.update(sql, note.c_id(), note.type(), note.note_display_name(), note.note_filename(), note.note_id());
    }

    @Override
    public int deleteNote(Integer note_id) {
        String sql = "DELETE FROM notes WHERE note_id = ?";
        return jdbcTemplate.update(sql, note_id);
    }
}