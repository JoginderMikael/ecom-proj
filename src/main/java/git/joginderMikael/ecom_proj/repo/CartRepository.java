package git.joginderMikael.ecom_proj.repo;

import git.joginderMikael.ecom_proj.model.Cart;
import git.joginderMikael.ecom_proj.model.EcomUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(EcomUsers users);
}
