package gr.symfoititis.rest.teacher.rowMappers;

import gr.symfoititis.rest.teacher.records.Note;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NotesRowMapper implements RowMapper<Note> {
    @Override
    public Note mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Note(
                rs.getInt("note_id"),
                rs.getInt("c_id"),
                rs.getString("type"),
                rs.getString("note_display_name"),
                rs.getString("note_filename")
                );
    }
}
