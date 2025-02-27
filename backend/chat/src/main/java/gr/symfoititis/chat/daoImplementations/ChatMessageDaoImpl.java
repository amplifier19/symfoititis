package gr.symfoititis.chat.daoImplementations;

import gr.symfoititis.chat.dao.ChatMessageDao;
import gr.symfoititis.chat.entities.ChatMessage;
import gr.symfoititis.chat.rowMappers.ChatMessagesRowMapper;
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
        """;
        return jdbcTemplate.update(sql, chatMessage.getRoom(), chatMessage.getDep_id(), chatMessage.getC_id(),
                chatMessage.getIs_teacher(), chatMessage.getSender_id(), chatMessage.getRecipient_id(),
                chatMessage.getType(), chatMessage.getContent());
    }
}
