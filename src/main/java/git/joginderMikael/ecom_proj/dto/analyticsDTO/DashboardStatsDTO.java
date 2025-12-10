package git.joginderMikael.ecom_proj.dto.analyticsDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardStatsDTO {

    private long totalUsers;
    private long totalOrders;
    private double totalRevenue;
    private long totalProducts;
}
