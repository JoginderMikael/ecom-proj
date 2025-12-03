package git.joginderMikael.ecom_proj.service;

import git.joginderMikael.ecom_proj.dto.UserRegistrationRequest;
import git.joginderMikael.ecom_proj.model.EcomUsers;
import git.joginderMikael.ecom_proj.model.Role;
import git.joginderMikael.ecom_proj.repo.EcomUsersRepo;
import git.joginderMikael.ecom_proj.repo.RoleRepostory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class EcomUsersService {
    @Autowired
    private EcomUsersRepo ecomUsersRepo;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTService jwtService;

    @Autowired
    RoleRepostory roleRepostory;


    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    public EcomUsers registerUser(UserRegistrationRequest user) {

        //resolving roles
        Set<Role> userRoles = user.getRoles().stream()
                .map(roleName -> roleRepostory.findByName(roleName))
 //                              .orElseThrow(() -> new RoleNotFoundException("Role Not Found: " + roleName)))
                .collect(Collectors.toSet());
        //create new user object
        EcomUsers newUser = new EcomUsers();
        //newuser username
        newUser.setUsername(user.getUsername());

        //new user password
        newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        //new user roles
        newUser.setRoles(userRoles);
        return ecomUsersRepo.save(newUser);
    }

    public String verify(EcomUsers user) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if(authentication.isAuthenticated()){
            //return "Login Successful";
           return jwtService.generateToken(user.getUsername());
        }else{
            return "Fail";
        }
    }
}
