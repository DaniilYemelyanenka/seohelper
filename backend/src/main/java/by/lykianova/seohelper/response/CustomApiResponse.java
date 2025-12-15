package by.lykianova.seohelper.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CustomApiResponse<T> {
    private boolean success;
    private T data;

}
