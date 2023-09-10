package kr.co.test.study.demo.domain.item.service;

import kr.co.test.study.demo.interfaces.order.OrderDto;
import kr.co.test.study.demo.domain.item.dto.ItemDto;
import kr.co.test.study.demo.domain.item.entity.Item;

import java.util.List;

public interface ItemService {

    List<ItemDto> retrieveItemAllInfo();

    List<ItemDto> retrieveItemInfoList(List<OrderDto.OrderItemRequest> requests);

    Item retrieveItemInfo(String itemNo);

}
