package git.joginderMikael.ecom_proj.service;

import git.joginderMikael.ecom_proj.model.EcomUsers;
import git.joginderMikael.ecom_proj.repo.EcomUsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

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
                .authorities(user.getRoles().stream()
                    .map(r-> new SimpleGrantedAuthority(r.getName()))
                    .collect(Collectors.toList()))
                .build();
    }

}
