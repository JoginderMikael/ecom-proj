package git.joginderMikael.ecom_proj.service;

import git.joginderMikael.ecom_proj.dto.OrderRequestItem;
import git.joginderMikael.ecom_proj.model.*;
import git.joginderMikael.ecom_proj.repo.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private EcomUsersRepo ecomUsersRepo;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    CartRepository cartRepository;


    public Order placeOrder(int userId, List<OrderRequestItem> cartItems, ShippingAddress address) {

        EcomUsers user = ecomUsersRepo.findById(userId)
                .orElseThrow(()-> new RuntimeException("User Not Found!"));
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(()->new RuntimeException("Cart Not found!"));

        Order order = new Order();
        order.setUser(user);
        order.setCreatedAt(LocalDate.from(LocalDateTime.now()));
        order.setStatus(OrderStatus.PENDING);
        order.setShippingAddress(address);

        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0.0;

        for(OrderRequestItem cartItem : cartItems){

            Product product = productRepo.findById(cartItem.getProductId())
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

        order.setItems(orderItems);
        order.setTotalAmount(total);
        orderRepo.save(order);

        //clearing purchased items from the cart
        for(OrderRequestItem requestItem:cartItems){
            Product product = productRepo.findById(requestItem.getProductId()).get();
            cartItemRepository.findByCartAndProduct(cart, product)
                    .ifPresent(cartItemRepository::delete);
        }

        //recalculating the cart total
        double newCartTotal = cart.getItems()
                .stream()
                .mapToDouble(CartItem::getPriceAtAdd)
                .sum();

        cart.setTotal(newCartTotal);
        cartRepository.save(cart);

        return order;
//        order.setTotalAmount(total);
//        return  orderRepo.save(order);
    }

    public Order updateOrderStatus(Long orderId, String status) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));
        OrderStatus newStatus;

        try{
            newStatus = OrderStatus.valueOf(status.toUpperCase());
        } catch(IllegalStateException e){
            throw  new RuntimeException("Invalid status: " + status);
        }

        //validating the status change
        validateStatusChange(order.getStatus(), newStatus);

        order.setStatus(newStatus);
        return orderRepo.save(order);
    }

    private void validateStatusChange(OrderStatus current, OrderStatus newStatus) {
        switch (current){
            case PENDING -> {
                if(newStatus != OrderStatus.PAID && newStatus != OrderStatus.CANCELLED){
                    throw new RuntimeException("PENDING can only move to PAID or CANCELLED");
                }
                break;
            }
            case PAID -> {
                if(newStatus != OrderStatus.SHIPPED){
                    throw new RuntimeException("PAID can only move to SHIPPED!");
                }
                break;
            }
            case SHIPPED -> {
                if(newStatus != OrderStatus.DELIVERED){
                    throw new RuntimeException("SHIPPED can only move to DELIVERED");
                }
                break;
            }
            case DELIVERED -> {
                if(newStatus != OrderStatus.RETURNED) {
                    throw new RuntimeException("DELIVERED orders can only move to RETURNED");
                }
                break;
            }
            case CANCELLED -> throw new RuntimeException("CANCELLED orders cannot be updated");
            default -> throw new RuntimeException("Invalid Status transition");
        }

    }
}
