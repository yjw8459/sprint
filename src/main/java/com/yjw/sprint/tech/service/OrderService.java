package com.yjw.sprint.tech.service;

import com.yjw.sprint.tech.dto.OrderDTO;
import com.yjw.sprint.tech.dto.enumerate.DeliveryStatus;
import com.yjw.sprint.tech.dto.enumerate.OrderStatus;
import com.yjw.sprint.tech.entity.Item;
import com.yjw.sprint.tech.entity.Member;
import com.yjw.sprint.tech.entity.Order;
import com.yjw.sprint.tech.entity.OrderItem;
import com.yjw.sprint.tech.repository.ItemRepository;
import com.yjw.sprint.tech.repository.MemberRepository;
import com.yjw.sprint.tech.repository.OrderRepository;
import com.yjw.sprint.tech.statemachine.event.OrderEvents;
import com.yjw.sprint.tech.statemachine.service.OrderStateEventService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService {

    private MemberRepository memberRepository;
    private OrderRepository orderRepository;

    private ItemRepository itemRepository;

    private OrderStateEventService stateEventService;

    public OrderService(MemberRepository memberRepository,
                        OrderRepository orderRepository,
                        ItemRepository itemRepository,
                        OrderStateEventService stateEventService) {
        this.memberRepository = memberRepository;
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.stateEventService = stateEventService;
    }

    public OrderDTO create(Long memberId, Long itemId, Long count){
        Member member = memberRepository.findById(memberId).get();
        Item item = itemRepository.findById(itemId).get();

        OrderItem orderItem = OrderItem.createOrderItem(item, 100, count);

        Order order = Order.createOrder(member, orderItem);
        order.setDeliveryStatus(DeliveryStatus.ORDER);
        order.setOrderStatus(OrderStatus.ORDER);
        Order result = orderRepository.save(order);
        return result.toDto();
    }

    public OrderDTO update(OrderDTO orderDTO){
        Order order = orderRepository.save(orderDTO.toEntity());
        return order.toDto();
    }

    public OrderDTO delete(Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null){
            order.cancelOrder(order);
        }
        return order.toDto();
    }

    @Transactional(readOnly = true)
    public Page<OrderDTO> searchOrders(Pageable pageable){
        return orderRepository.findAll(pageable).map(Order::toDto);
    }

    public void changeState(Long orderId, OrderEvents events){
        stateEventService.changeState(orderId, events);
    }
}
