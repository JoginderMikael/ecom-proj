package git.joginderMikael.ecom_proj.service;

import git.joginderMikael.ecom_proj.model.EcomUsers;
import git.joginderMikael.ecom_proj.repo.EcomUsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EcomUsersService {
    @Autowired
    private EcomUsersRepo ecomUsersRepo;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
    public EcomUsers registerUser(EcomUsers user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return ecomUsersRepo.save(user);
    }
}
