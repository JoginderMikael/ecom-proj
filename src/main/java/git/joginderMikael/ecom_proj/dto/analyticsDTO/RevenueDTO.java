package git.joginderMikael.ecom_proj.dto.analyticsDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevenueDTO {
    private double totalRevenue;
    private double revenueThisMonth;
    private double revenueToday;
}
