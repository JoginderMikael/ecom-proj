package git.joginderMikael.ecom_proj.controller;

import git.joginderMikael.ecom_proj.dto.OrderRequestItem;
import git.joginderMikael.ecom_proj.model.Order;
import git.joginderMikael.ecom_proj.model.ShippingAddress;
import git.joginderMikael.ecom_proj.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin //handle CORS Error
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    OrderService orderService;


    @PostMapping("/place")
    public ResponseEntity<Order> placeOrder(@RequestParam int userId,
                                            @RequestBody List<OrderRequestItem> cartItems,
                                            @RequestBody ShippingAddress address){
        Order order = orderService.placeOrder(userId, cartItems, address);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

}
