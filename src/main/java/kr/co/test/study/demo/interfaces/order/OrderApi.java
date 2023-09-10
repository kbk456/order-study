package kr.co.test.study.demo.interfaces.order;

import kr.co.test.study.demo.application.order.OrderFacade;
import kr.co.test.study.demo.domain.order.dto.OrderInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;




@Service
@RequiredArgsConstructor
public class OrderApi {

    private final OrderFacade orderFacade;
    private final OrderDtoMapper orderDtoMapper;

    //주문 등록
    public Long registerOrder(OrderDto.RegisterOrderRequest request) {
        var orderCommand = orderDtoMapper.of(request);
        var orderId = orderFacade.registerOrder(orderCommand);
        return orderId;
    }

    //주문 결제
    public void requestOrder(Long orderId) {
        orderFacade.paymentOrder(orderId);
    }

    //주문 조회
    public OrderInfo.Main retrieveOrder(Long orderId) {
        return orderFacade.retrieveOrder(orderId);
    }


}
