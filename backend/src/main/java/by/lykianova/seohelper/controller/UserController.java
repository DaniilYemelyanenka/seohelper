package by.lykianova.seohelper.controller;

import by.lykianova.seohelper.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/vi/users")
public class UserController {

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
