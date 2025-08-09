package gr.symfoititis.chat.rowMappers;

import gr.symfoititis.chat.records.ChatStats;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChatStatsRowMapper implements RowMapper<ChatStats> {

    @Override
    public ChatStats mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ChatStats(
                rs.getString("room"),
                rs.getInt("unread_count"),
                rs.getInt("last_read_message_id")
        );
    }
}
