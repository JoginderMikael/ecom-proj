package git.joginderMikael.ecom_proj.controller;

import git.joginderMikael.ecom_proj.dto.OrderRequestItem;
import git.joginderMikael.ecom_proj.dto.PlaceOrderRequest;
import git.joginderMikael.ecom_proj.model.Order;
import git.joginderMikael.ecom_proj.model.OrderEvent;
import git.joginderMikael.ecom_proj.model.ShippingAddress;
import git.joginderMikael.ecom_proj.repo.OrderEventRepository;
import git.joginderMikael.ecom_proj.service.OrderService;
import git.joginderMikael.ecom_proj.service.PaymentService;
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

    @Autowired
    PaymentService  paymentService;


    private final OrderEventRepository orderEventRepository;

    public OrderController(OrderEventRepository orderEventRepository) {
        this.orderEventRepository = orderEventRepository;
    }
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


    @PostMapping("/{id}/pay")
    public ResponseEntity<Order> payOrder(
            @PathVariable Long id,
            @RequestParam String provider,
            @RequestParam String transactionId
    ){
        return new ResponseEntity<>(paymentService.processPayment(id, provider, transactionId), HttpStatus.OK);
    }

    @GetMapping("/{id}/events")
    public ResponseEntity<List<OrderEvent>> getOrderEvents(@PathVariable Long id) {
        return ResponseEntity.ok(orderEventRepository.findByOrderIdOrderByCreatedAtAsc(id));
    }

}
