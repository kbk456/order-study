package kr.co.test.study.demo.domain.item.service;

import kr.co.test.study.demo.interfaces.order.OrderDto;
import kr.co.test.study.demo.common.exception.SoldOutException;
import kr.co.test.study.demo.domain.item.dto.ItemDto;
import kr.co.test.study.demo.domain.item.entity.Item;
import kr.co.test.study.demo.domain.mapper.ItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemReader itemReader;
    private final ItemMapper itemMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ItemDto> retrieveItemAllInfo() {
        var item = itemReader.getItemAll();
        return itemMapper.toDto(item);
    }

    @Override
    public List<ItemDto> retrieveItemInfoList(List<OrderDto.OrderItemRequest> requests) {
            // 아이템 번호 추출
            List<String> itemNumbers = requests.stream()
                    .map(OrderDto.OrderItemRequest::getItemNo)
                    .collect(Collectors.toList());

            // 아이템 조회
            List<Item> items = itemReader.findByItemNoIn(itemNumbers);

            // 주문된 아이템 정렬
            List<OrderDto.OrderItemRequest> sortedOrderItems = sortOrderItems(requests);

            // 재고 체크
            checkItemStocks(items, sortedOrderItems);

            // DTO로 변환
            return itemMapper.toDto(items);
        }
    private void checkItemStocks(List<Item> items, List<OrderDto.OrderItemRequest> orderItemRequests) {
        try {
            for (int i = 0; i < items.size(); i++) {
                items.get(i).checkItemStock(orderItemRequests.get(i).getOrderCount());
            }
        } catch (SoldOutException e) {
            throw e;
        }
    }

    private List<OrderDto.OrderItemRequest> sortOrderItems(List<OrderDto.OrderItemRequest> requests) {
        return requests.stream()
                .sorted(Comparator.comparing(OrderDto.OrderItemRequest::getItemNo))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Item retrieveItemInfo(String itemNo) {
        return itemReader.getItemById(itemNo);
    }



}
