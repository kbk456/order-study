package kr.co.test.study.demo.domain.order.entity;

import kr.co.test.study.demo.domain.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
@NoArgsConstructor
@Table(name = "orders")
public class Order extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<OrderItem> orderItemList = new ArrayList<>();

    private ZonedDateTime orderedAt;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Getter
    @RequiredArgsConstructor
    public enum Status{
        INIT("주문시작"),
        ORDER_COMPLETE("주문완료");

        private final String desc;
    }

    public void orderComplete(){
        this.status = Status.ORDER_COMPLETE;
    }

    //총 지불 금액 계산(배송비추가)
    public Long calculateTotalAmount(){
        long sum = orderItemList.stream()
                .mapToLong(OrderItem::calculateTotalAmount)
                .sum();
        if(sum < 50000){
            sum += 2500;
        }
        return sum;
    }

    //총 주문 금액 계산
    public Long calculateTotalOrderAmount(){
        return orderItemList.stream()
                .mapToLong(OrderItem::calculateTotalAmount)
                .sum();
    }

    @Builder
    public Order(
            Long userId
    ) {
        this.userId = userId;
        this.orderedAt = ZonedDateTime.now();
        this.status = Status.INIT;
    }

}
