package online.shop.SmartphoneStore.exception;

import online.shop.SmartphoneStore.exception.custom.AddressOverLimitException;
import online.shop.SmartphoneStore.exception.custom.DataNotFoundException;
import online.shop.SmartphoneStore.exception.custom.UniqueConstraintException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
                .badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(errors);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ExceptionMessage> dataNotFoundExceptionHandler(
            DataNotFoundException exception
    ){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        new ExceptionMessage(exception.getMessage())
                );
    }

    @ExceptionHandler(AddressOverLimitException.class)
    public ResponseEntity<ExceptionMessage> addressOverLimitExceptionHandler(
            AddressOverLimitException exception
    ){
        return ResponseEntity
                .badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        new ExceptionMessage(exception.getMessage())
                );
    }

    @ExceptionHandler(UniqueConstraintException.class)
    public ResponseEntity<Map<String, String>> uniqueConstraintExceptionHandler(
            UniqueConstraintException exception
    ){
        return ResponseEntity
                .badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        exception.getColumns()
                );
    }
}
