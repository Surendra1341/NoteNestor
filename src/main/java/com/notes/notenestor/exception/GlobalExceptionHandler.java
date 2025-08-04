package com.notes.notenestor.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<?> handleException(Exception e) {
//        log.error("GlobalExceptionHandler :: handleException ::"+e.getMessage());
//        return  new ResponseEntity<>("error hai :"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
       List<ObjectError> allErrors = e.getBindingResult().getAllErrors();

        Map<String,Object> error= new LinkedHashMap<>();

       allErrors.forEach(objectError -> {
          String msg = objectError.getDefaultMessage();
           String field = ((FieldError)(objectError)).getField();
            error.put(field, msg);
       });
       return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleNullPointerException(NullPointerException e) {
        log.error("GlobalExceptionHandler :: handleNullPointerException ::"+e.getMessage());
    return  new ResponseEntity<>("error null pointer  :"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(Exception e) {
        log.error("GlobalExceptionHandler :: handle ResourceNotFoundException ::"+e.getMessage());
        return  new ResponseEntity<>("error hai :"+e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException e) {
        log.error("GlobalExceptionHandler :: handle ValidationException ::"+e.getError());
        return  new ResponseEntity<>(e.getError(), HttpStatus.NOT_FOUND);
    }


}
