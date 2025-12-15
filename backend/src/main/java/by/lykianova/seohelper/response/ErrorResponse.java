package by.lykianova.seohelper.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class ErrorResponse {

    private LocalDate timestamp;
    private HttpStatus status;
    private String message;


}
