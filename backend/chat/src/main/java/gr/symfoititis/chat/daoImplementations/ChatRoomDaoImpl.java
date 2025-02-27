package gr.symfoititis.chat.daoImplementations;

import gr.symfoititis.chat.dao.ChatRoomDao;
import gr.symfoititis.chat.rowMappers.ChatRoomRowMapper;
import gr.symfoititis.common.entities.ChatRoom;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ChatRoomDaoImpl implements ChatRoomDao {
    private final JdbcTemplate jdbcTemplate;
    public ChatRoomDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Optional<ChatRoom> getChatRoom(String roomId) {
        String sql = "SELECT * FROM chat_rooms where room = ?";
        return jdbcTemplate.query(sql, new ChatRoomRowMapper(), roomId).stream().findFirst();
    }

    @Override
    public boolean studentBelongsToChatRoom(String s_id, String roomId) {
        String sql = "SELECT EXISTS (SELECT 1 FROM chat_rooms where s_id = ? AND room = ?)";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, s_id, roomId));
    }

    @Override
    public boolean teacherBelongsToChatRoom(String t_id, String roomId) {
        String sql = "SELECT EXISTS (SELECT 1 FROM chat_rooms where t_id = ? AND room = ?)";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, t_id, roomId));
    }

    @Override
    public int addChatRoom(ChatRoom chatRoom) {
        String sql = """
              INSERT INTO chat_rooms (room, dep_id, c_id, t_id, s_id)
              VALUES (?, ?, ?, ?, ?)
        """;
        return jdbcTemplate.update(sql, chatRoom.getRoom(), chatRoom.getDep_id(),
                chatRoom.getC_id(), chatRoom.getT_id(), chatRoom.getS_id());
    }
}
