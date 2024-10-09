package gr.symfoititis.admin.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PAYLOAD_TOO_LARGE)
public class ContentTooLargeException extends RuntimeException {
    public ContentTooLargeException (String message) {
        super (message);
    }
}
