package gr.symfoititis.common.records;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public record  Response (Integer status, String timestamp, Object data) {
    public Response (Integer status, Object data) {
        this(status, getTimestamp(), data);
    }

    private static String getTimestamp () {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC+0"));
        return sdf.format(now);
    }
}
