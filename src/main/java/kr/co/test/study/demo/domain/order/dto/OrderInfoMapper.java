package kr.co.test.study.demo.domain.order.dto;

import kr.co.test.study.demo.domain.order.entity.Order;
import kr.co.test.study.demo.domain.order.entity.OrderItem;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface OrderInfoMapper {

    @Mappings({
            @Mapping(source = "order.id", target = "orderId"),
            @Mapping(expression = "java(order.calculateTotalAmount())", target = "totalAmount"),
            @Mapping(expression = "java(order.calculateTotalOrderAmount())", target = "orderAmount"),
    })
    OrderInfo.Main of(Order order, List<OrderItem> orderItemList);

    @Mappings({
            @Mapping(expression = "java(orderItem.calculateTotalAmount())", target = "totalAmount")
    })
    OrderInfo.OrderItem of(OrderItem orderItem);

}
