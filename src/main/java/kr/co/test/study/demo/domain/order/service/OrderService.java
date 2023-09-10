package kr.co.test.study.demo.domain.order.service;

import kr.co.test.study.demo.domain.order.dto.OrderCommand;
import kr.co.test.study.demo.domain.order.dto.OrderInfo;

public interface OrderService {

    Long registerOrder(OrderCommand.RegisterOrder registerOrder);

    void paymentOrder(Long orderId);

    OrderInfo.Main retrieveOrder(Long orderId);

}
