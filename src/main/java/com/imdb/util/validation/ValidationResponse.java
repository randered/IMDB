package com.imdb.util.validation;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ValidationResponse {

    private String httpStatus;
    private final String dateTime;
    private final Set<String> messages;

    public ValidationResponse() {
        this.messages = new HashSet<>();
        this.dateTime = LocalDateTime.now().toString();
        this.httpStatus = HttpStatus.OK.toString();
    }

    public ValidationResponse(String message) {
        this();
        this.messages.add(message);
    }

    public ValidationResponse(String message, HttpStatus status) {
        this(message);
        this.httpStatus = status.toString();
    }

    public ValidationResponse(Set<String> messages, HttpStatus status){
        this.messages = messages;
        this.dateTime = LocalDateTime.now().toString();
        this.httpStatus = status.toString();
    }

    public String getMessage(int index) {
        int count = 0;
        for (String message : this.messages) {
            if(count == index){
                return message;
            }
            count++;
        }
        throw new IndexOutOfBoundsException();
    }
}
