package gr.symfoititis.chat.services;

import gr.symfoititis.chat.dao.ChatMessageDao;
import gr.symfoititis.chat.dao.ChatRoomDao;
import gr.symfoititis.chat.entities.ChatMessage;
import gr.symfoititis.common.entities.ChatRoom;
import gr.symfoititis.common.exceptions.ForbiddenException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChatService {
    private final ChatRoomDao chatRoomDao;
    private final ChatMessageDao chatMessageDao;
    public ChatService(ChatRoomDao chatRoomDao, ChatMessageDao chatMessageDao) {
        this.chatRoomDao = chatRoomDao;
        this.chatMessageDao = chatMessageDao;
    }

    @RabbitListener(queues = "booking-chat")
    public void consumeMessageFromRabbitmq(List<ChatRoom> chatRooms) {
        for (ChatRoom chatRoom : chatRooms) {
            if (chatRoomDao.getChatRoom(chatRoom.getRoom()).isEmpty()) {
                chatRoomDao.addChatRoom(chatRoom);
            }
        }
    }

    public List<ChatMessage> getMessages(String roomId, int offset) {
        return chatMessageDao.getMessages(roomId, offset);
    }

    public void userBelongsToChatRoom(String role, String id, String roomId) {
        boolean belongs = switch(role) {
            case "student" -> chatRoomDao.studentBelongsToChatRoom(id, roomId);
            case "teacher" -> chatRoomDao.teacherBelongsToChatRoom(id, roomId);
            default -> throw new ForbiddenException("Invalid role");
        };
        if (!belongs) {
            throw new ForbiddenException("Access to the requested resource is forbidden");
        }
    }

    public int addMessage(ChatMessage chatMessage) {
        String input, roomId;
        if (chatMessage.getIs_teacher()) {
            input = chatMessage.getDep_id() + chatMessage.getC_id() + chatMessage.getSender_id() + chatMessage.getRecipient_id();
        } else {
            input = chatMessage.getDep_id() + chatMessage.getC_id() + chatMessage.getRecipient_id() + chatMessage.getSender_id();
        }
        roomId = UUID.nameUUIDFromBytes(input.getBytes()).toString();
        Optional<ChatRoom> chatRoom = chatRoomDao.getChatRoom(roomId);
        if (chatRoom.isEmpty()) {
            throw new ForbiddenException("No suitable chat room found");
        }
        return chatMessageDao.addMessage(chatMessage);
    }
}
