package kr.co.test.study.demo.domain.item.entity;

import kr.co.test.study.demo.common.exception.SoldOutException;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import static kr.co.test.study.demo.domain.item.entity.Item.Status.SOLD_OUT;


@NoArgsConstructor
@Getter
@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemNo;

    private String itemName;

    private Long itemPrice;

    private Integer itemStock;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Getter
    @RequiredArgsConstructor
    public enum Status{
        ON_SALE("판매중"),
        SOLD_OUT("판매종료");

        private final String desc;
    }

    //재고 감소 메서드
    public void reduceItemStock(Integer orderQuantity){
        if(this.itemStock < orderQuantity){
            throw new SoldOutException();
        }
        this.itemStock  = this.itemStock - orderQuantity;
    }

    //재고 체크 메서드
    public void checkItemStock(Integer orderQuantity){
        if (this.itemStock < orderQuantity){
            throw new SoldOutException();
        }
    }

    //상품 상태 변경 메서드(SOLD_OUT)
    public void changeSoldOut(){
        this.status = SOLD_OUT;
    }

    //주문 가능 상품 체크 메서드
    public boolean isSales() {
        return this.status == Status.ON_SALE;
    }

}
