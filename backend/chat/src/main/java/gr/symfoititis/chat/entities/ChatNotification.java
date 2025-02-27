package gr.symfoititis.chat.entities;

public class ChatNotification {
    private String room;
    private String sender_id;
    private String recipient_id;
    private String type;
    private String content;

    public ChatNotification () {}
    public ChatNotification(String room, String sender_id, String recipient_id, String type, String content) {
        this.room = room;
        this.sender_id = sender_id;
        this.recipient_id = recipient_id;
        this.type = type;
        this.content = content;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getRecipient_id() {
        return recipient_id;
    }

    public void setRecipient_id(String recipient_id) {
        this.recipient_id = recipient_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
