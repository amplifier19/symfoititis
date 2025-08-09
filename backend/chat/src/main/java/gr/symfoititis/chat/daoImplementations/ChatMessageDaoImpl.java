package gr.symfoititis.chat.daoImplementations;

import gr.symfoititis.chat.dao.ChatMessageDao;
import gr.symfoititis.chat.entities.ChatMessage;
import gr.symfoititis.chat.records.ChatStats;
import gr.symfoititis.chat.rowMappers.ChatMessagesRowMapper;
import gr.symfoititis.chat.rowMappers.ChatStatsRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChatMessageDaoImpl implements ChatMessageDao {
    private final JdbcTemplate jdbcTemplate;

    public ChatMessageDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<ChatMessage> getMessages(String roomId, int offset) {
        String sql = "SELECT * FROM chat_messages WHERE room = ? ORDER BY created_at DESC LIMIT 16 OFFSET ?";
        return jdbcTemplate.query(sql, new ChatMessagesRowMapper(), roomId, offset);
    }

    @Override
    public int addMessage(ChatMessage chatMessage) {
        String sql = """
                INSERT INTO chat_messages (room , dep_id, c_id, is_teacher, sender_id, recipient_id, type, content)
                VALUES (?, ?, ?, ?, ?, ?, ?::message_type, ?)
                RETURNING message_id
        """;
        return jdbcTemplate.queryForObject(sql, Integer.class,
                chatMessage.getRoom(),
                chatMessage.getDep_id(),
                chatMessage.getC_id(),
                chatMessage.getIs_teacher(),
                chatMessage.getSender_id(),
                chatMessage.getRecipient_id(),
                chatMessage.getType(),
                chatMessage.getContent()
        );
    }

    @Override
    public int incrementUnreadCount(Integer messageId, String roomId, String participantId) {
        String sql = """
                UPDATE unread_messages
                SET unread_count = unread_count + 1
                WHERE room = ? AND participant_id = ?
        """;
        return jdbcTemplate.update(sql, roomId, participantId);
    }

    @Override
    public void readMessages(Integer messageId, String roomId, String participantId) {
        String sql = """
                UPDATE unread_messages
                SET
                    unread_count = 0,
                    last_read_message_id = GREATEST(last_read_message_id, ?)
                WHERE room = ? AND participant_id = ?
        """;
        jdbcTemplate.update(sql, messageId, roomId, participantId);
    }

    @Override
    public List<ChatStats> getChatStats(String userId) {
        String sql = """
        SELECT
            me.room, me.unread_count, other.last_read_message_id
        FROM unread_messages AS me
        JOIN unread_messages AS other
            ON other.room = me.room
            AND other.participant_id != ?
        WHERE me.participant_id = ?
        """;
        return jdbcTemplate.query(sql, new ChatStatsRowMapper(), userId, userId);
    }
}
