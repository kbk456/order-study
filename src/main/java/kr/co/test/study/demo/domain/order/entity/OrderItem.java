package kr.co.test.study.demo.domain.order.entity;

import kr.co.test.study.demo.common.exception.InvalidParamException;
import kr.co.test.study.demo.domain.AbstractEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Entity
@Getter
@NoArgsConstructor
@Table(name="order_items")
public class OrderItem extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private Integer orderCount;

    private String itemNo;

    private String itemName;

    private Long itemPrice;

    //주문 금액 계산
    public Long calculateTotalAmount(){
        return itemPrice * orderCount;
    }

    @Builder
    public OrderItem(
            Order order,
            Integer orderCount,
            String itemNo,
            String itemName,
            Long itemPrice
    ) {
        if (order == null) throw new InvalidParamException("OrderItemLine.order");
        if (orderCount == null) throw new InvalidParamException("OrderItemLine.orderCount");
        if (itemNo == null && StringUtils.isEmpty(itemName))
            throw new InvalidParamException("OrderItemLine.itemNo and itemName");
        if (itemPrice == null) throw new InvalidParamException("OrderItemLine.itemPrice");

        this.order = order;
        this.orderCount = orderCount;
        this.itemNo = itemNo;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }
}
