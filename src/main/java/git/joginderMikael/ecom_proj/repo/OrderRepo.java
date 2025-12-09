package git.joginderMikael.ecom_proj.repo;

import git.joginderMikael.ecom_proj.model.Order;
import git.joginderMikael.ecom_proj.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
    List<Order> findByStatus(OrderStatus status);
}
