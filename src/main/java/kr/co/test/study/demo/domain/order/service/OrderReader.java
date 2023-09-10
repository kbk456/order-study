package kr.co.test.study.demo.domain.order.service;

import kr.co.test.study.demo.domain.order.entity.Order;

public interface OrderReader {
    Order getOrder(Long orderId);
}
