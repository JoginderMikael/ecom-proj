package git.joginderMikael.ecom_proj.service;

import git.joginderMikael.ecom_proj.model.Order;
import git.joginderMikael.ecom_proj.model.OrderStatus;
import git.joginderMikael.ecom_proj.repo.OrderRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AdminOrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private OrderService orderService;

    public List<Order> getAllOrders(){
        return orderRepo.findAll();
    }

    public List<Order> getOrderByStatus(OrderStatus orderStatus){
        return orderRepo.findByStatus(orderStatus);
    }


    public Order updateStatus(Long id, OrderStatus orderStatus){
       return orderService.updateOrderStatus(id, String.valueOf(orderStatus));
    }
}
