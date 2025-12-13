package git.joginderMikael.ecom_proj.service;


import git.joginderMikael.ecom_proj.model.Order;
import git.joginderMikael.ecom_proj.model.OrderEvent;
import git.joginderMikael.ecom_proj.model.OrderEventType;
import git.joginderMikael.ecom_proj.repo.OrderEventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderEventService {

    private final OrderEventRepository orderEventRepository;
    public OrderEventService(OrderEventRepository orderEventRepository) {
        this.orderEventRepository = orderEventRepository;
    }

    public void logEvent(Order order, OrderEventType orderEventType, String message) {
        OrderEvent event = OrderEvent.builder()
                .order(order)
                .eventType(orderEventType)
                .message(message)
                .createdAt(LocalDateTime.now())
                .build();

        orderEventRepository.save(event);

    }
}
