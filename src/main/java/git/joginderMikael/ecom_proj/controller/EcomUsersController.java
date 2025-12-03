package git.joginderMikael.ecom_proj.controller;

import git.joginderMikael.ecom_proj.dto.UserRegistrationRequest;
import git.joginderMikael.ecom_proj.model.EcomUsers;
import git.joginderMikael.ecom_proj.service.EcomUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class EcomUsersController {

    @Autowired
    private EcomUsersService ecomUserService;

    @PostMapping("/register")
    public EcomUsers registerUser(@RequestBody UserRegistrationRequest userRegistrationRequest) {
        return ecomUserService.registerUser(userRegistrationRequest);
    }

    @PostMapping("/login")
    public String login(@RequestBody EcomUsers user){
        return  ecomUserService.verify(user);
    }
}
