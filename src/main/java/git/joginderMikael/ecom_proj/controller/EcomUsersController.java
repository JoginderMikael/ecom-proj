package git.joginderMikael.ecom_proj.controller;

import git.joginderMikael.ecom_proj.model.EcomUsers;
import git.joginderMikael.ecom_proj.service.EcomUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class EcomUsersController {

    @Autowired
    private EcomUsersService ecomUserService;

    public EcomUsers registerUser(@RequestBody EcomUsers user) {
        return ecomUserService.registerUser(user);
    }
}
