package gr.symfoititis.chat.dao;


import gr.symfoititis.chat.entities.ChatMessage;

import java.util.List;

public interface ChatMessageDao {
    List<ChatMessage> getMessages(String roomId, int offset);
    int addMessage(ChatMessage chatMessage);
}
