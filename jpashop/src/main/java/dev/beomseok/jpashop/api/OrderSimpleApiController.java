package dev.beomseok.jpashop.api;

import dev.beomseok.jpashop.domain.Address;
import dev.beomseok.jpashop.domain.Order;
import dev.beomseok.jpashop.domain.OrderSearch;
import dev.beomseok.jpashop.domain.OrderStatus;
import dev.beomseok.jpashop.repository.OrderRepository;
import dev.beomseok.jpashop.repository.order.simplequery.OrderSimpleQueryDto;
import dev.beomseok.jpashop.repository.order.simplequery.OrderSimpleQueryRepository;
import dev.beomseok.jpashop.service.OrderService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2(){
        List<Order> orders = orderRepository.findAll();
        for (Order order : orders) {
            System.out.println("order.getId() = " + order.getId());
        }
        return orders.stream()
                .map(SimpleOrderDto::new)
                .collect(Collectors.toList());
    }
    
    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> orderV3(){
        List<Order> orders = orderRepository.findAllWithDelivery();
        return orders.stream()
                .map(SimpleOrderDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> orderV4(){
        return orderSimpleQueryRepository.findAllWithDelivery();
    }


    @Data
    static class SimpleOrderDto{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order){
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
        }
    }
}
