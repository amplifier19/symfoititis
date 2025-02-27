package gr.symfoititis.chat.dao;

import gr.symfoititis.common.entities.ChatRoom;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ChatRoomDao {
    Optional<ChatRoom> getChatRoom(String roomId);
    boolean teacherBelongsToChatRoom(String t_id, String roomId);
    boolean studentBelongsToChatRoom(String s_id, String roomId);
    int addChatRoom(ChatRoom chatRoom);
}
