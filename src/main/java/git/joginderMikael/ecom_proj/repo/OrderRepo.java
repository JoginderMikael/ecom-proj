package git.joginderMikael.ecom_proj.repo;

import git.joginderMikael.ecom_proj.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Long> {
}
