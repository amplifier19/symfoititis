package gr.symfoititis.chat.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ReadReceipt {
    String receipt_id;

    @Positive
    int message_id;

    @NotNull
    @NotBlank
    String room;

    @NotNull
    @NotBlank
    String sender_id;

    @NotNull
    @NotBlank
    String recipient_id;

    public ReadReceipt() { }

    public ReadReceipt(String receipt_id, int message_id, String room, String sender_id, String recipient_id) {
        this.receipt_id = receipt_id;
        this.message_id = message_id;
        this.room = room;
        this.sender_id = sender_id;
        this.recipient_id = recipient_id;
    }

    public String getReceipt_id() {
        return receipt_id;
    }

    public void setReceipt_id(String receipt_id) {
        this.receipt_id = receipt_id;
    }

    public int getMessage_id() {
        return message_id;
    }

    public void setMessage_id(int message_id) {
        this.message_id = message_id;
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
}
