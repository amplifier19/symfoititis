package gr.symfoititis.common.repositories;

import gr.symfoititis.common.dao.NotesDao;
import gr.symfoititis.common.records.Note;
import gr.symfoititis.common.rowMappers.NotesRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public List<Note> getNotes(Integer c_id) {
        String sql = "SELECT * FROM notes n WHERE n.c_id = ? ORDER BY n.note_display_name";
        return jdbcTemplate.query(sql, new NotesRowMapper(), c_id);
    }
}
