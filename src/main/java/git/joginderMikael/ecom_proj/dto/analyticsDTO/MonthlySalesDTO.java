package git.joginderMikael.ecom_proj.dto.analyticsDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlySalesDTO {
    private String month;
    private double sales;
}
