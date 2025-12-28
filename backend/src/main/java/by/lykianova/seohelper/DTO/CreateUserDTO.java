package by.lykianova.seohelper.DTO;

import lombok.Data;

@Data
public class CreateUserDTO {

    private String email;

    private String passwordHash;

}
