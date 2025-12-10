package git.joginderMikael.ecom_proj.repo;

import git.joginderMikael.ecom_proj.dto.analyticsDTO.MonthlySalesDTO;
import git.joginderMikael.ecom_proj.dto.analyticsDTO.TopProductDTO;
import git.joginderMikael.ecom_proj.model.Order;
import git.joginderMikael.ecom_proj.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
    List<Order> findByStatus(OrderStatus status);

    @Query("""
    SELECT SUM(o.totalAmount)
    FROM Order o
    WHERE o.status = 'COMPLETED'
    """)
    Double sumTotalRevenue();

    @Query("""
        SELECT new git.joginderMikael.ecom_proj.dto.analyticsDTO.MonthlySalesDTO(
        FUNCTION('TO_CHAR', o.createdAt, 'YYYY-MM'),
        SUM(o.totalAmount)
    )
    FROM Order o
    WHERE o.status = 'COMPLETED'
    GROUP BY FUNCTION('TO_CHAR', o.createdAt, 'YYYY-MM')
    ORDER BY FUNCTION('TO_CHAR', o.createdAt, 'YYYY-MM')
    """)
    List<MonthlySalesDTO> getMonthlySales();


    @Query("""
    SELECT SUM(o.totalAmount) FROM Order o
    WHERE o.status = 'COMPLETED'
        AND FUNCTION('DATE_TRUNC', 'month', o.createdAt) = FUNCTION('DATE_TRUNC', 'month', CURRENT_TIMESTAMP())
    """)
    Double sumRevenueThisMonth();


    @Query("""
SELECT SUM(o.totalAmount)
FROM Order o
WHERE o.status = 'COMPLETED'
    AND FUNCTION('DATE', o.createdAt) = FUNCTION('CURRENT_DATE')
""")
    Double sumRevenueToday();



    @Query("""
SELECT new git.joginderMikael.ecom_proj.dto.analyticsDTO.TopProductDTO(
    p.id,
    p.name,
    SUM(oi.quantity)
)
FROM OrderItem oi
JOIN oi.product p
JOIN oi.order o
WHERE o.status = 'COMPLETED'
GROUP BY p.id, p.name
ORDER BY SUM(oi.quantity) DESC
""")
    List<TopProductDTO> getTopSellingProducts();
}
