package kr.co.test.study.demo.interfaces.item;

import kr.co.test.study.demo.application.item.ItemFacade;
import kr.co.test.study.demo.interfaces.order.OrderDto;
import kr.co.test.study.demo.domain.item.dto.ItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemApi {

    private final ItemFacade itemFacade;

    //아이템 전체 조회
    public List<ItemDto> retrieveItemAll(){
        return itemFacade.retrieveItemAllInfo();
    }

    //주문 요청 아이템 조회
    public List<ItemDto> retrieveItemInfoList(List<OrderDto.OrderItemRequest> request){
        return itemFacade.retrieveItemInfoList(request);
    }
}
