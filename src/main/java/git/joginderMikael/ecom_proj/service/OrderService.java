package git.joginderMikael.ecom_proj.service;

import git.joginderMikael.ecom_proj.dto.OrderRequestItem;
import git.joginderMikael.ecom_proj.model.*;
import git.joginderMikael.ecom_proj.repo.EcomUsersRepo;
import git.joginderMikael.ecom_proj.repo.OrderRepo;
import git.joginderMikael.ecom_proj.repo.ProductRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private EcomUsersRepo ecomUsersRepo;


    public Order placeOrder(int userId, List<OrderRequestItem> cartItems, ShippingAddress address) {

        EcomUsers user = ecomUsersRepo.findById(userId)
                .orElseThrow(()-> new RuntimeException("User Not Found!"));

        Order order = new Order();
        order.setUser(user);
        order.setCreatedAt(LocalDate.from(LocalDateTime.now()));
        order.setStatus(OrderStatus.PENDING);
        order.setShippingAddress(address);

        double total = 0;

        for(OrderRequestItem cartItem : cartItems){

            Product product = productRepo.findById(Math.toIntExact(cartItem.getProductId()))
                    .orElseThrow(() -> new RuntimeException("Product Not Found!"));
            if(product.getStockQuantity() < cartItem.getQuantity()){
                throw new RuntimeException("Insufficient stock: "+ product.getName());
            }

            //update the db products
            product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());

            if(product.getStockQuantity() == 0){
                product.setProductAvailable(false);
            }

            productRepo.save(product);

            //continue with ordering
            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(cartItem.getQuantity());
            item.setPriceAtPurchase(product.getPrice());

            order.addItem(item);
            total += item.getPriceAtPurchase() * item.getQuantity();
        }

        order.setTotalAmount(total);
        return  orderRepo.save(order);
    }
}
