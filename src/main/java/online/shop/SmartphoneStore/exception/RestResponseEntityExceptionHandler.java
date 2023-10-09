package online.shop.SmartphoneStore.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import online.shop.SmartphoneStore.entity.Smartphone;
import online.shop.SmartphoneStore.exception.custom.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
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

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> constraintViolationException(
            ConstraintViolationException exception
    ){
        Map<String, String> errors = exception
                .getConstraintViolations()
                .stream()
                .collect(Collectors.toMap(
                        (e) -> {
                            int index = e.getPropertyPath().toString().indexOf(".");
                            return e.getPropertyPath().toString().substring(index + 1);
                        },
                        ConstraintViolation::getMessage
                ));
        return ResponseEntity
                .badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(errors);
    }

    @ExceptionHandler(QuantityInStockIsEmptyException.class)
    public ResponseEntity<List<Smartphone>> quantityInStockIsEmptyExceptionHandler(
            QuantityInStockIsEmptyException e
    ){
        return ResponseEntity
                .badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                    e.getSmartphone()
                );
    }

    @ExceptionHandler(RatingOverLimitException.class)
    public ResponseEntity<ExceptionMessage> ratingOverLimitException(
            RatingOverLimitException exception
    ){
        return ResponseEntity
                .badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        new ExceptionMessage(exception.getMessage())
                );
    }
}
