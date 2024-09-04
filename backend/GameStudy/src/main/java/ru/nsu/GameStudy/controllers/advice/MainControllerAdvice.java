package ru.nsu.GameStudy.controllers.advice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.webjars.NotFoundException;
import ru.nsu.GameStudy.exceptions.ValueAlreadyExistsException;
import ru.nsu.GameStudy.exceptions.UserAlreadyExistsException;

@ControllerAdvice
@RequiredArgsConstructor
public class MainControllerAdvice {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException() {
        return new ResponseEntity<String>("Not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            UserAlreadyExistsException.class,
            ValueAlreadyExistsException.class
    })
    public ResponseEntity<String> handleAlreadyExistsException() {
        return new ResponseEntity<String>(
                "Such value already exists in table", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalCallerException.class)
    public ResponseEntity<String> handleIllegalCallerException() {
        return new ResponseEntity<String>(
                "Illegal method call", HttpStatus.BAD_REQUEST);
    }
}