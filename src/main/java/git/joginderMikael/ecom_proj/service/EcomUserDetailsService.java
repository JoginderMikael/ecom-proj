package git.joginderMikael.ecom_proj.service;

import git.joginderMikael.ecom_proj.model.EcomUsers;
import git.joginderMikael.ecom_proj.repo.EcomUsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class EcomUserDetailsService implements UserDetailsService{

    @Autowired
    private EcomUsersRepo ecomUsersRepo;

    public UserDetails loadUserByUsername(String username) {
        EcomUsers user = ecomUsersRepo.findByUsername(username);

        if(user == null){
            throw new RuntimeException("User not found");
        }
        return  User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles("USER") // You can set roles as per your requirement
                .build();
    }

}
