package gr.symfoititis.chat.rowMappers;

import gr.symfoititis.common.entities.ChatRoom;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChatRoomRowMapper implements RowMapper<ChatRoom> {
    @Override
    public ChatRoom mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ChatRoom(
                rs.getInt("chat_id"),
                rs.getString("room"),
                rs.getInt("dep_id"),
                rs.getInt("c_id"),
                rs.getString("t_id"),
                rs.getString("s_id")
        );
    }
}
