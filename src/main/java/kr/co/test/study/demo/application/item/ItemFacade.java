package kr.co.test.study.demo.application.item;

import kr.co.test.study.demo.domain.item.dto.ItemDto;
import kr.co.test.study.demo.domain.item.service.ItemService;
import kr.co.test.study.demo.interfaces.order.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemFacade {

    private final ItemService itemService;

    //상품 전체 조회
    public List<ItemDto> retrieveItemAllInfo(){
        return itemService.retrieveItemAllInfo();
    }

    //상품 조회
    public List<ItemDto> retrieveItemInfoList(List<OrderDto.OrderItemRequest> requests){
        return itemService.retrieveItemInfoList(requests);
    }

}
