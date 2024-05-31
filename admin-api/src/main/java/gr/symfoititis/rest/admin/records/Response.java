package gr.symfoititis.rest.admin.records;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public record Response(Object data, String message, Integer status) {
}
