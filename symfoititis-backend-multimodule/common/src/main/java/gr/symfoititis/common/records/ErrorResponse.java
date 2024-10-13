package gr.symfoititis.common.records;

import static gr.symfoititis.common.utils.UtcTimestamp.getTimestamp;

public record ErrorResponse(Integer status, String timestamp, String error, String path) {
    public ErrorResponse(Integer status, String error, String path) {
        this(status, getTimestamp(), error, path);
    }
}
