package by.lykianova.seohelper.controller;

import by.lykianova.seohelper.DTO.ShowUserDTO;
import by.lykianova.seohelper.DTO.UserDTO;
import by.lykianova.seohelper.config.UserPrincipals;
import by.lykianova.seohelper.response.CustomApiResponse;
import by.lykianova.seohelper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("me")
    public ResponseEntity<ShowUserDTO> getUserInformation(@AuthenticationPrincipal UserPrincipals userPrincipals){
        ShowUserDTO showUserDTO = userService.getUserInfo(userPrincipals.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(showUserDTO);
    }

    @PutMapping("update")
    public ResponseEntity<CustomApiResponse<String>> updateUserInformation(@RequestBody UserDTO updateUserDTO,
                                                                   @AuthenticationPrincipal UserPrincipals userPrincipals){
        userService.updateUserInformation(updateUserDTO,userPrincipals.getUsername());
        return  ResponseEntity.status(HttpStatus.OK).body(
                new CustomApiResponse<>(true,"updated")
        );
    }

    @DeleteMapping("delete")
    public ResponseEntity<String> deleteUser(@AuthenticationPrincipal UserPrincipals userPrincipals){
        userService.deleteUser(userPrincipals.getUsername());
        //TODO after deleting must exit from account
        return ResponseEntity.status(HttpStatus.OK).body("deleted");
    }

}
