package git.joginderMikael.ecom_proj.dto;

import git.joginderMikael.ecom_proj.model.ShippingAddress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceOrderRequest {
    private int userId;
    private List<OrderRequestItem> cartItems;
    private ShippingAddress address;
}
