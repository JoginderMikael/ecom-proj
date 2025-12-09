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

    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.status ='COMPLETED'")
    Double sumTotalRevenue();

    @Query("""
    SELECT new git.joginderMikael.ecom_proj.dto.analyticsDTO.MonthlySalesDTO(
        FUNCTION('DATE_FORMAT', o.createdAt, '%Y-%m'),
            SUM(o.totalAmount)
        )
            FROM Order o
                WHERE o.status = 'COMPLETED'
                    GROUP BY FUNCTION('DATE_FORMAT', o.createdAt, '%Y-%m')
                        ORDER BY FUNCTION('DATE_FORMAT', o.createdAt, '%Y-%m')
    """)
    List<MonthlySalesDTO> getMonthlySales();


    @Query("""
    SELECT SUM(o.totalAmount) FROM Order o
        WHERE o.status = 'COMPLETED'
            AND MONTH(o.createdAt) = MONTH(CURRENT_DATE)
                AND YEAR(o.createdAt) = YEAR(CURRENT_DATE)
    """)
    Double sumRevenueThisMonth();


    @Query("""
    SELECT SUM(o.totalAmount)
        FROM Order o
            WHERE o.status = 'COMPLETED'
                AND DATE(o.createdAt) = CURRENT_DATE
    """)
    Double sumRevenueToday();



    @Query("""
    SELECT new git.joginderMikael.ecom_proj.dto.analyticsDTO.MonthlySalesDTO(
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
