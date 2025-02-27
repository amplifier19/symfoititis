package gr.symfoititis.chat.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.sql.Timestamp;

public class ChatMessage {
    @Positive
    private Integer message_id;
    @NotNull
    @NotBlank
    private String room;
    @NotNull
    @Positive
    private Integer dep_id;
    @NotNull
    @Positive
    private Integer c_id;
    @NotNull
    private Boolean is_teacher;
    @NotNull
    @NotBlank
    private String sender_id;
    @NotNull
    @NotBlank
    private String recipient_id;
    @NotNull
    @NotBlank
    private String type;
    @NotNull
    @NotBlank
    private String content;
    private Timestamp created_at;

    public ChatMessage () {}

    public ChatMessage(String room, Integer dep_id, Integer c_id, Boolean is_teacher, String sender_id, String recipient_id, String type, String content) {
        this.room = room;
        this.dep_id = dep_id;
        this.c_id = c_id;
        this.is_teacher = is_teacher;
        this.sender_id = sender_id;
        this.recipient_id = recipient_id;
        this.type = type;
        this.content = content;
    }

    public ChatMessage(@NotNull Integer message_id, String room, Integer dep_id, Integer c_id, Boolean is_teacher, String sender_id, String recipient_id, String type, String content, Timestamp created_at) {
        this.message_id = message_id;
        this.room = room;
        this.dep_id = dep_id;
        this.c_id = c_id;
        this.is_teacher = is_teacher;
        this.sender_id = sender_id;
        this.recipient_id = recipient_id;
        this.type = type;
        this.content = content;
        this.created_at = created_at;
    }

    public Integer getMessage_id() {
        return message_id;
    }

    public void setMessage_id(Integer message_id) {
        this.message_id = message_id;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Integer getDep_id() {
        return dep_id;
    }

    public void setDep_id(Integer dep_id) {
        this.dep_id = dep_id;
    }

    public Integer getC_id() {
        return c_id;
    }

    public void setC_id(Integer c_id) {
        this.c_id = c_id;
    }

    public Boolean getIs_teacher() {
        return is_teacher;
    }

    public void setIs_teacher(Boolean is_teacher) {
        this.is_teacher = is_teacher;
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

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
}
