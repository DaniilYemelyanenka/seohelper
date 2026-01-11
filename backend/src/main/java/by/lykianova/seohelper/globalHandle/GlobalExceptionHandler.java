package by.lykianova.seohelper.globalHandle;

import by.lykianova.seohelper.error.*;
import by.lykianova.seohelper.response.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> catchArgumentNotValid(MethodArgumentNotValidException exception) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(
                LocalDate.now(),
                HttpStatus.BAD_REQUEST,
                exception.getBindingResult().getAllErrors().stream()
                        .map(error -> {
                            return ((FieldError) error).getField() + ": " + error.getDefaultMessage();
                        })
                        .collect(Collectors.joining(", ")
                        ))
        );
    }

    @ExceptionHandler(SiteException.class)
    public ResponseEntity<ErrorResponse> catchSiteException(SiteException siteException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponse(
                        LocalDate.now(),
                        HttpStatus.NOT_FOUND,
                        siteException.getMessage()
                )
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> catchEntityNotFound(EntityNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(
                LocalDate.now(),
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        ));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> catchUserNotFoundException(UserNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorResponse(
                        LocalDate.now(),
                        HttpStatus.NOT_FOUND,
                        exception.getMessage()
                )
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> catchConstraintValidationException(ConstraintViolationException exception){
        ConstraintViolation<?> violation =
                exception.getConstraintViolations().iterator().next();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponse(
                        LocalDate.now(),
                        HttpStatus.BAD_REQUEST,
                        violation.getMessage()
                )
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> catchRuntimeException(RuntimeException runtimeException){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorResponse(
                        LocalDate.now(),
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Something was wrong"
                )
        );
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ErrorResponse> catchEmailNotFoundException(EmailNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorResponse(
                        LocalDate.now(),
                        HttpStatus.NOT_FOUND,
                        exception.getMessage()
                )
        );
    }

    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<ErrorResponse> catchUserExistsExceptionException(UserExistsException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorResponse(
                        LocalDate.now(),
                        HttpStatus.NOT_FOUND,
                        exception.getMessage()
                )
        );
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> catchAuthenticationException(AuthenticationException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorResponse(
                        LocalDate.now(),
                        HttpStatus.NOT_FOUND,
                        exception.getMessage()
                )
        );
    }
}
