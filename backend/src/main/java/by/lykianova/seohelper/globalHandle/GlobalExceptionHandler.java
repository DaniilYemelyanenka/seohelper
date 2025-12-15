package by.lykianova.seohelper.globalHandle;

import by.lykianova.seohelper.error.SiteException;
import by.lykianova.seohelper.response.ErrorResponse;
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
}
