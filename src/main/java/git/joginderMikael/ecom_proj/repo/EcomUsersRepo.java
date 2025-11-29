package git.joginderMikael.ecom_proj.repo;

import git.joginderMikael.ecom_proj.model.EcomUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EcomUsersRepo extends JpaRepository<EcomUsers, Integer> {

    EcomUsers findByUsername(String username);
}
