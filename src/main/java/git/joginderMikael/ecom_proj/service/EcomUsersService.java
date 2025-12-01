package git.joginderMikael.ecom_proj.service;

import git.joginderMikael.ecom_proj.model.EcomUsers;
import git.joginderMikael.ecom_proj.repo.EcomUsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class EcomUsersService {
    @Autowired
    private EcomUsersRepo ecomUsersRepo;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
    public EcomUsers registerUser(EcomUsers user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return ecomUsersRepo.save(user);
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
