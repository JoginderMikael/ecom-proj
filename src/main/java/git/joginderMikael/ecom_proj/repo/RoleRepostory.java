package git.joginderMikael.ecom_proj.repo;

import git.joginderMikael.ecom_proj.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepostory extends JpaRepository<Role, String> {
    Role findByName(String roleName);
}
