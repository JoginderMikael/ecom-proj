package git.joginderMikael.ecom_proj.controller;

import git.joginderMikael.ecom_proj.dto.OrderRequestItem;
import git.joginderMikael.ecom_proj.dto.PlaceOrderRequest;
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
    public ResponseEntity<Order> placeOrder(@RequestBody PlaceOrderRequest request){

        Order order = orderService.placeOrder(
                request.getUserId(),
                request.getCartItems(),
                request.getAddress()
        );

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<Order> updateStatus(
            @PathVariable Long orderId,
            @RequestParam String status
    ){
        Order updated = orderService.updateOrderStatus(orderId, status);
        return  new ResponseEntity<>(updated, HttpStatus.OK);
    }

}
