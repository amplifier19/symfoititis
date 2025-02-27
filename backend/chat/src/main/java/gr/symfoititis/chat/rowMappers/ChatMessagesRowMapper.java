package gr.symfoititis.chat.rowMappers;

import gr.symfoititis.chat.entities.ChatMessage;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChatMessagesRowMapper implements RowMapper<ChatMessage> {
    @Override
    public ChatMessage mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ChatMessage(
                rs.getInt("message_id"),
                rs.getString("room"),
                rs.getInt("dep_id"),
                rs.getInt("c_id"),
                rs.getBoolean("is_teacher"),
                rs.getString("sender_id"),
                rs.getString("recipient_id"),
                rs.getString("type"),
                rs.getString("content"),
                rs.getTimestamp("created_at")
        );
    }
}
