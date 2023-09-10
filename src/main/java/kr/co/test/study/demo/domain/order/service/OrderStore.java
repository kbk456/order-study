package kr.co.test.study.demo.domain.order.service;

import kr.co.test.study.demo.domain.order.entity.Order;
import kr.co.test.study.demo.domain.order.entity.OrderItem;

public interface OrderStore {
    Order store(Order order);

    OrderItem store(OrderItem orderItem);

}
