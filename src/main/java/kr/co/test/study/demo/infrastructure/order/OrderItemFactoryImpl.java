package kr.co.test.study.demo.infrastructure.order;

import kr.co.test.study.demo.domain.item.service.ItemReader;
import kr.co.test.study.demo.domain.order.dto.OrderCommand;
import kr.co.test.study.demo.domain.order.entity.Order;
import kr.co.test.study.demo.domain.order.entity.OrderItem;
import kr.co.test.study.demo.domain.order.service.OrderItemFactory;
import kr.co.test.study.demo.domain.order.service.OrderStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class OrderItemFactoryImpl implements OrderItemFactory {
    private final ItemReader itemReader;
    private final OrderStore orderStore;

    @Override
    public List<OrderItem> store(Order order, OrderCommand.RegisterOrder requestOrder) {
        return requestOrder.getOrderItemList().stream()
                .map(orderItemRequest -> {
                    var item = itemReader.getItemBy(orderItemRequest.getItemNo());
                    var initOrderItem = orderItemRequest.toEntity(order, item);
                    var orderItem = orderStore.store(initOrderItem);
                    return orderItem;
                }).collect(Collectors.toList());
    }

}
