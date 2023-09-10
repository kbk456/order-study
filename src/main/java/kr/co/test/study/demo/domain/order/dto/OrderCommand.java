package kr.co.test.study.demo.domain.order.dto;

import kr.co.test.study.demo.domain.order.entity.Order;
import kr.co.test.study.demo.domain.item.entity.Item;
import kr.co.test.study.demo.domain.order.entity.OrderItem;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

public class OrderCommand {

    @Getter
    @Builder
    @ToString
    public static class RegisterOrder {
        private final Long userId;
        private final List<RegisterOrderItem> orderItemList;

        public Order toEntity() {
            return Order.builder()
                    .userId(userId)
                    .build();
        }
    }

        @Getter
        @Builder
        @ToString
        public static class RegisterOrderItem {
            private final Integer orderCount;
            private final String itemName;
            private final Long itemPrice;
            private final String itemNo;

            public OrderItem toEntity(Order order, Item item) {
                return OrderItem.builder()
                        .order(order)
                        .orderCount(orderCount)
                        .itemNo(item.getItemNo())
                        .itemName(itemName)
                        .itemPrice(itemPrice)
                        .build();
            }
        }

    @Getter
    @Builder
    @ToString
    public static class PaymentRequest {
        private final Long orderId;
        private final Long amount;
        //private final PayMethod payMethod;
    }

}
