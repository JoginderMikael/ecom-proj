package git.joginderMikael.ecom_proj.dto;

import lombok.Data;

@Data
public class CartUpdateRequest {

    private Long productId;
    private int quantity;
}
