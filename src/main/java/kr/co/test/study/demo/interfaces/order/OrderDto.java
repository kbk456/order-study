package kr.co.test.study.demo.interfaces.order;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

public class OrderDto {

    @Getter
    @Setter
    @ToString
    public static class RegisterOrderRequest {

        private Long userId;

        private List<RegisterOrderItem> orderItemList;
    }

    @Getter
    @Setter
    @ToString
    public static class RegisterOrderItem {

        private Integer orderCount;

        private String itemNo;

        private String itemName;

        private Long itemPrice;

    }

    @Getter
    @Setter
    @ToString
    public static class OrderItemRequest {

        private String itemNo;

        private Integer orderCount;

    }


    // 조회
    @Getter
    @Builder
    @ToString
    public static class Main {
        private final Long userId;
        private final String payMethod;
        private final Long totalAmount;
        private final String orderedAt;
        private final String status;
        private final String statusDescription;
        private final List<OrderItem> orderItemList;
    }


    @Getter
    @Builder
    @ToString
    public static class OrderItem {
        private final Integer orderCount;
        private final Long itemNo;
        private final String itemName;
        private final Long totalAmount;
        private final Long itemPrice;

    }


}
