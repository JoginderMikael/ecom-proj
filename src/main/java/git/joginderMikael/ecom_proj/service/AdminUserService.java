package git.joginderMikael.ecom_proj.service;

import git.joginderMikael.ecom_proj.model.EcomUsers;
import git.joginderMikael.ecom_proj.model.Role;
import git.joginderMikael.ecom_proj.repo.EcomUsersRepo;
import git.joginderMikael.ecom_proj.repo.RoleRepo;
import jakarta.transaction.Transactional;
import org.hibernate.annotations.SecondaryRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdminUserService {

    @Autowired
    private EcomUsersRepo ecomUsersRepo;


    @Autowired
    private RoleRepo roleRepo;


    public List<EcomUsers> listAllUsers() {
        return ecomUsersRepo.findAll();
    }

    public EcomUsers updateUserRoles(int userId, Set<String> roleNames) {
        EcomUsers ecomUsers = ecomUsersRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Set<Role> roles = roleNames.stream()
                .map(name -> roleRepo.findByName(name))
                        //.orElseThrow(() -> new RuntimeException("Role not found: " + name)))
                .collect(Collectors.toSet());

        ecomUsers.setRoles(roles);
        return ecomUsersRepo.save(ecomUsers);
    }

    public EcomUsers blockUser(int userId) {
        EcomUsers user = ecomUsersRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setAccountLocked(true);
        return ecomUsersRepo.save(user);
    }

    public EcomUsers unBlockUser(int userId) {
        EcomUsers user = ecomUsersRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setAccountLocked(false);
        return ecomUsersRepo.save(user);
    }
}
