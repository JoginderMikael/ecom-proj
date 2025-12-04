package git.joginderMikael.ecom_proj.dto;

import lombok.Data;

@Data
public class RemoveCartItemRequest {
    private Long productId;
    private int quantity;
}
