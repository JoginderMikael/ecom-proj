package git.joginderMikael.ecom_proj.repo;

import git.joginderMikael.ecom_proj.model.Cart;
import git.joginderMikael.ecom_proj.model.CartItem;
import git.joginderMikael.ecom_proj.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartProduct(Cart cart, Product product);
}
