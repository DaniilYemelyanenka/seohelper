package by.lykianova.seohelper.controller;

import by.lykianova.seohelper.DTO.CreateUserDTO;
import by.lykianova.seohelper.entity.User;
import by.lykianova.seohelper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public void register(@RequestBody CreateUserDTO createUserDTO){
        userService.addUser(createUserDTO);
    }

    @GetMapping("me")
    public ResponseEntity<String> getUserInformation(@RequestParam(value = "id") Long id){
        //TODO get User
        return ResponseEntity.status(HttpStatus.OK).body("me");
    }

    @PutMapping("update")
    public ResponseEntity<String> updateUserInformation(@RequestBody User user){
        //TODO update user information
        return  ResponseEntity.status(HttpStatus.OK).body("update");
    }

    @DeleteMapping("delete")
    public ResponseEntity<String> deleteUser(@RequestParam(value = "id") Long id){
        //TODO delete user
        return ResponseEntity.status(HttpStatus.OK).body("delete");
    }

}
