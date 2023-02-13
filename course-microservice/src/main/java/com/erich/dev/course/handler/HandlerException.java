package com.erich.dev.course.handler;

import com.erich.dev.course.exception.NotFoundException;
import com.erich.dev.course.exception.ServerErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class HandlerException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handlerValidation(MethodArgumentNotValidException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        Map<String, String> map = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(err -> {
            String field = ((FieldError)err).getField();
            String messa = err.getDefaultMessage();
            map.put(field, messa);
        });
        problemDetail.setTitle("BAD REQUEST");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setProperty("invalid fields", map);
        return problemDetail;
    }

    @ExceptionHandler(NotFoundException.class)
    public  ProblemDetail handlerNotFoundException(NotFoundException e){
        ProblemDetail problemDetail  = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Not Found");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setProperty("hora :", LocalTime.now());
        return problemDetail;
    }

    @ExceptionHandler(ServerErrorException.class)
    public  ProblemDetail handlerNotFoundException(ServerErrorException e){
        ProblemDetail problemDetail  = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setTitle("Internal_Server_Error");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setProperty("hora :", LocalTime.now());
        return problemDetail;
    }
}
