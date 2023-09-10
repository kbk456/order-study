package kr.co.test.study.demo.domain.item.dto;

import kr.co.test.study.demo.domain.item.entity.Item;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDto {

    private Long id;

    private String itemNo;

    private String itemName;

    private Long itemPrice;

    private Integer itemStock;

    private Integer itemOrderCount;

    private Item.Status status;

}
