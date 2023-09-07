package online.shop.SmartphoneStore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> methodArgumentNotValidExceptionHandler(
            MethodArgumentNotValidException exception
    ){
        Map<String, String> errors = exception.getBindingResult().getFieldErrors()
                                                .stream()
                                                .collect(
                                                        Collectors.toMap(
                                                                FieldError::getField,
                                                                FieldError::getDefaultMessage
                                                        )
                                                );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errors);
    }
}
