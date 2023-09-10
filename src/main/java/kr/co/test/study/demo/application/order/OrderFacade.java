package kr.co.test.study.demo.application.order;

import kr.co.test.study.demo.domain.order.dto.OrderCommand;
import kr.co.test.study.demo.domain.order.dto.OrderInfo;
import kr.co.test.study.demo.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderFacade {

    private final OrderService orderService;

    //주문 등록
    public Long registerOrder(OrderCommand.RegisterOrder registerOrder) {
        var orderId = orderService.registerOrder(registerOrder);
        return orderId;
    }


    //주문 결제
    public void paymentOrder(Long orderId) {
        orderService.paymentOrder(orderId);
    }
    //주문 조회(주문 등록된 건 조회)
    //주문 상품, 갯수, 주문금액, 배송비, 지불금액
    public OrderInfo.Main retrieveOrder(Long orderId) {
        return orderService.retrieveOrder(orderId);
    }

}
