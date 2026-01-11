package by.lykianova.seohelper.controller;

import by.lykianova.seohelper.DTO.CreateUserDTO;
import by.lykianova.seohelper.DTO.UserDTO;
import by.lykianova.seohelper.response.CustomApiResponse;
import by.lykianova.seohelper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<CustomApiResponse<String>> registerUser(@Validated @RequestBody CreateUserDTO createUserDTO){

        String token = userService.addUser(createUserDTO);

        return ResponseEntity.status(HttpStatus.OK).body(
                new CustomApiResponse<>(true,token)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<CustomApiResponse<String>> loginUser(@Validated @RequestBody UserDTO userDTO){

        String token = userService.login(userDTO);

        return ResponseEntity.status(HttpStatus.OK).body(new CustomApiResponse<>(true,token));
    }
}
