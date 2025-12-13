package git.joginderMikael.ecom_proj.repo;

import git.joginderMikael.ecom_proj.model.OrderEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface OrderEventRepository extends JpaRepository<OrderEvent,Long> {
    List<OrderEvent> findByOrderByCreatedAtAsc(Long orderId);
}
