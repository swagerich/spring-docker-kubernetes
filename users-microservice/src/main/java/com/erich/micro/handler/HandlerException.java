package com.erich.micro.handler;


import com.erich.micro.exception.BadRequestException;
import com.erich.micro.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;

@RestControllerAdvice
public class HandlerException {

    @ExceptionHandler(BadRequestException.class)
    public ProblemDetail handlerBadRequestException(BadRequestException e){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Bad Request");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setProperty("Hora:", LocalDate.now());

        return problemDetail;
    }

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handlerNotFoundxEception(NotFoundException e){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Not-Found");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setProperty("Hora:", LocalDate.now());

        return problemDetail;
    }
}
