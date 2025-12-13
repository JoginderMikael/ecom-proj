package git.joginderMikael.ecom_proj.service;


import git.joginderMikael.ecom_proj.model.Order;
import git.joginderMikael.ecom_proj.model.OrderStatus;
import git.joginderMikael.ecom_proj.model.Payment;
import git.joginderMikael.ecom_proj.model.PaymentStatus;
import git.joginderMikael.ecom_proj.repo.OrderRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
public class PaymentService {

    @Autowired
    OrderRepo orderRepo;

    public Order processPayment(Long orderId, String provider, String transactionID){
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

       if(order.getStatus() != OrderStatus.PENDING){
           throw new RuntimeException("Only PENDING orders can be paid!");
       }

        Payment payment = new Payment();
        payment.setProvider(provider);
        payment.setTransactionId(transactionID);
        payment.setAmount(order.getTotalAmount());
        payment.setPaymentStatus(PaymentStatus.PAID);
        payment.setPaidAt(LocalDateTime.now());

        order.setPayment(payment);
        order.setStatus(OrderStatus.PAID);

        return orderRepo.save(order);
    }
}
