package dev.beomseok.boardserver.dto.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@NoArgsConstructor
public class ResponseBody<T> {

    private HttpStatus status;
    private T content;
    private String message;

    public ResponseBody(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseBody(HttpStatus status, T content) {
        this.status = status;
        this.content = content;
        this.message = "";
    }

    public ResponseBody(HttpStatus status) {
        this.status = status;
    }

    public static ResponseEntity<ResponseBody> createFailResponse(HttpStatus status, String message){
        ResponseBody responseBody = new ResponseBody<>(status, message);
        return ResponseEntity.status(status).body(responseBody);
    }

    public static<T> ResponseEntity<ResponseBody<T>> createSuccessResponse(HttpStatus status, T content){
        ResponseBody responseBody = new ResponseBody<>(status, content);
        return ResponseEntity.status(status).body(responseBody);
    }
}