package kr.co.test.study.demo.domain.order.service;

import kr.co.test.study.demo.domain.order.dto.OrderCommand;
import kr.co.test.study.demo.domain.order.entity.Order;
import kr.co.test.study.demo.domain.order.entity.OrderItem;

import java.util.List;

public interface OrderItemFactory {
    List<OrderItem> store(Order order, OrderCommand.RegisterOrder requestOrder);
}
