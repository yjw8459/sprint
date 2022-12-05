package com.yjw.sprint.tech.service;

import com.yjw.sprint.tech.dto.OrderDTO;
import com.yjw.sprint.tech.dto.OrderItemDTO;
import com.yjw.sprint.tech.dto.enumerate.OrderStatus;
import com.yjw.sprint.tech.entity.Item;
import com.yjw.sprint.tech.entity.Member;
import com.yjw.sprint.tech.entity.Order;
import com.yjw.sprint.tech.entity.OrderItem;
import com.yjw.sprint.tech.repository.ItemRepository;
import com.yjw.sprint.tech.repository.MemberRepository;
import com.yjw.sprint.tech.repository.OrderRepository;
import com.yjw.sprint.tech.statemachine.service.StateEventService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class OrderService {

    private MemberRepository memberRepository;
    private OrderRepository orderRepository;

    private ItemRepository itemRepository;

    private StateEventService stateEventService;

    public OrderService(MemberRepository memberRepository,
                        OrderRepository orderRepository,
                        ItemRepository itemRepository,
                        StateEventService stateEventService) {
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
        Order result = orderRepository.save(order);
        stateEventService.createOrderState(result.getId());
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

    public Optional<OrderDTO> orderState(Long orderId){
        return orderRepository.findById(orderId).map(order -> {
            order.setOrderStatus(OrderStatus.ORDER);
            return order.toDto();
        });
    }

}
