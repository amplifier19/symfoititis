package gr.symfoititis.chat.dao;


import gr.symfoititis.chat.entities.ChatMessage;
import gr.symfoititis.chat.records.ChatStats;

import java.util.List;

public interface ChatMessageDao {
    List<ChatMessage> getMessages(String roomId, int offset);
    int addMessage(ChatMessage chatMessage);
    int incrementUnreadCount(Integer messageId, String roomId, String participantId);
    void readMessages(Integer messageId, String roomId, String participantId);
    List<ChatStats> getChatStats(String userId);
}
