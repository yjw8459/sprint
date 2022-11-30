package com.yjw.sprint.tech.service;

import com.yjw.sprint.tech.dto.OrderDTO;
import com.yjw.sprint.tech.dto.enumerate.DeliveryStatus;
import com.yjw.sprint.tech.entity.Item;
import com.yjw.sprint.tech.entity.Member;
import com.yjw.sprint.tech.entity.Order;
import com.yjw.sprint.tech.entity.OrderItem;
import com.yjw.sprint.tech.repository.ItemRepository;
import com.yjw.sprint.tech.repository.MemberRepository;
import com.yjw.sprint.tech.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

    private MemberRepository memberRepository;
    private OrderRepository orderRepository;

    private ItemRepository itemRepository;

    public OrderService(MemberRepository memberRepository, OrderRepository orderRepository, ItemRepository itemRepository) {
        this.memberRepository = memberRepository;
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
    }

    public OrderDTO create(Long memberId, Long itemId, Long count){
        Member member = memberRepository.findById(memberId).get();
        Item item = itemRepository.findById(itemId).get();

        OrderItem orderItem = OrderItem.createOrderItem(item, 100, count);

        Order order = Order.createOrder(member, orderItem);
        Order result = orderRepository.save(order);
        return result.toDto();
    }

    public OrderDTO update(OrderDTO orderDTO){
        // 주문수정
        Order order = orderRepository.save(orderDTO.toEntity());

        order.getOrderItems().forEach(orderItem -> {
            itemRepository.findById(orderItem.getItem().getId())
                    //.ifPresent(item -> item.setQuantity(item.getQuantity() + (orderItem.)));
        });


        return order.toDto();
    }

    @Transactional(readOnly = true)
    public Page<OrderDTO> searchOrders(Pageable pageable){
        return orderRepository.findAll(pageable).map(Order::toDto);
    }

}
