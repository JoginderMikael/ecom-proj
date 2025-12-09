package git.joginderMikael.ecom_proj.dto.analyticsDTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopProductDTO {
    private Long productId;
    private String productName;
    private long unitsDold;
}
