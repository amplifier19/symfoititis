package gr.symfoititis.common.records;

import static gr.symfoititis.common.utils.UtcTimestamp.getTimestamp;

public record  Response (Integer status, String timestamp, Object data) {
    public Response (Integer status, Object data) {
        this(status, getTimestamp(), data);
    }
}
