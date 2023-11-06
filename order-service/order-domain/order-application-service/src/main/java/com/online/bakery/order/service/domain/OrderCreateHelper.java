package com.online.bakery.order.service.domain;

import com.online.bakery.order.service.domain.dto.create.CreateOrderCommand;
import com.online.bakery.order.service.domain.entity.Bakery;
import com.online.bakery.order.service.domain.entity.Customer;
import com.online.bakery.order.service.domain.entity.Order;
import com.online.bakery.order.service.domain.event.OrderCreatedEvent;
import com.online.bakery.order.service.domain.exception.OrderDomainException;
import com.online.bakery.order.service.domain.mapper.OrderDataMapper;
import com.online.bakery.order.service.domain.ports.output.repository.BakeryRepository;
import com.online.bakery.order.service.domain.ports.output.repository.CustomerRepository;
import com.online.bakery.order.service.domain.ports.output.repository.OrderRepository;
import com.online.bakery.order.service.domain.valueobject.OrderDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class OrderCreateHelper {

    private final OrderDomainService orderDomainService;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final BakeryRepository bakeryRepository;
    private final OrderDataMapper orderDataMapper;

    public OrderCreateHelper(OrderDomainService orderDomainService, OrderRepository orderRepository,
                             CustomerRepository customerRepository, BakeryRepository bakeryRepository,
                             OrderDataMapper orderDataMapper) {
        this.orderDomainService = orderDomainService;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.bakeryRepository = bakeryRepository;
        this.orderDataMapper = orderDataMapper;
    }

    @Transactional
    public OrderCreatedEvent persistOrder(CreateOrderCommand createOrderCommand) {
        checkCustomer(createOrderCommand.getCustomerId());
        Bakery bakery = bakeryRepository.fetchBakeryInformation();
        Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order, bakery);
        Order orderResult = saveOrder(order);
        log.info("Order is created with id: {}", orderCreatedEvent.getOrder().getId().getValue());
        return orderCreatedEvent;
    }

    private void checkCustomer(UUID customerId) {
        Optional<Customer> customer = customerRepository.findCustomer(customerId);
        if (customer.isEmpty()) {
            log.warn("Could not find customer with customer id: {}", customerId);
            throw new OrderDomainException("Could not find customer with customer id: " + customer);
        }
    }

    private Order saveOrder(Order order) {
        Order orderResult = orderRepository.save(order);
        if (orderResult == null) {
            log.error("Could not save order!");
            throw new OrderDomainException("Could not save order!");
        }
        log.info("Order is saved with id: {}", orderResult.getId().getValue());
        return orderResult;
    }
}
