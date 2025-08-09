package gr.symfoititis.chat.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class MessageAck {
    @NotNull
    @NotBlank
    private String receipt_id;
    @Positive
    private int message_id;

    public MessageAck() {
    }

    public MessageAck(String receipt_id, int message_id) {
        this.receipt_id = receipt_id;
        this.message_id = message_id;
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
}
