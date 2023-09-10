package kr.co.test.study.demo.domain.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.List;

public class OrderInfo {

    @Getter
    @Builder
    @ToString
    public static class Main {
        private final Long orderId;
        private final Long userId;
        private final Long totalAmount;
        private final Long orderAmount;
        private final ZonedDateTime orderedAt;
        private final List<OrderItem> orderItemList;
    }


    @Getter
    @Builder
    @ToString
    public static class OrderItem {
        private final Integer orderCount;
        private final String itemNo;
        private final String itemName;
        private final Long totalAmount;
        private final Long itemPrice;
    }
}
