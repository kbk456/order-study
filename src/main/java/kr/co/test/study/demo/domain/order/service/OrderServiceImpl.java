package kr.co.test.study.demo.domain.order.service;


import kr.co.test.study.demo.domain.item.service.ItemReader;
import kr.co.test.study.demo.domain.order.dto.OrderCommand;
import kr.co.test.study.demo.domain.order.dto.OrderInfo;
import kr.co.test.study.demo.domain.order.dto.OrderInfoMapper;
import kr.co.test.study.demo.domain.order.entity.Order;
import kr.co.test.study.demo.domain.order.entity.OrderItem;
import kr.co.test.study.demo.common.exception.SoldOutException;
import kr.co.test.study.demo.domain.item.entity.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ItemReader itemReader;
    private final OrderReader orderReader;
    private final OrderStore orderStore;
    private final OrderItemFactory orderItemFactory;
    private final OrderInfoMapper orderInfoMapper;


    @Override
    @Transactional
    public Long registerOrder(OrderCommand.RegisterOrder registerOrder) {
        Order order = orderStore.store(registerOrder.toEntity());
        orderItemFactory.store(order, registerOrder);
        return order.getId();
    }

    @Override
    @Transactional
    public void paymentOrder(Long orderId) {
        //주문 정보 조회
        Order order = orderReader.getOrder(orderId);
        //재고 차감
        updateItemStock(order);
        //주문 상태 업데이트
        order.orderComplete();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderInfo.Main retrieveOrder(Long orderId) {
        var order = orderReader.getOrder(orderId);
        var orderItemList = order.getOrderItemList();
        return orderInfoMapper.of(order, orderItemList);
    }

    private void updateItemStock(Order order) {
        List<Item> item = findLockByItemNumbers(order);

        // item 및 OrderItemList을 정렬하여 정확한 대응을 유지
        //List<Item> sortedItem = sortItems(item);
        List<OrderItem> sortOrderItemList = sortOrderItems(order);

        try {
            reduceStocks(item, sortOrderItemList);
        } catch (SoldOutException e) {
            throw e;
        }
    }

    // 아이템 리스트를 아이템 번호로 오름차순 정렬하는 메서드
    private List<Item> sortItems(List<Item> items) {
        return items.stream()
                .sorted(Comparator.comparing(Item::getItemNo))
                .collect(Collectors.toList());
    }

    // 주문 아이템 리스트를 아이템 번호로 오름차순 정렬하는 메서드
    private List<OrderItem> sortOrderItems(Order order) {
        return order.getOrderItemList().stream()
                .sorted(Comparator.comparing(OrderItem::getItemNo))
                .collect(Collectors.toList());
    }

    // 주문 아이템의 재고를 업데이트하는 메서드
    private void reduceStocks(List<Item> item, List<OrderItem> orderItemList) {
        for (int i = 0; i < item.size(); i++) {
            item.get(i).reduceItemStock(orderItemList.get(i).getOrderCount());
        }
    }

    // 주문 아이템 번호로 아이템을 검색하는 메서드
    private List<Item> findLockByItemNumbers(Order order) {
        List<String> itemNumbers = order.getOrderItemList().stream()
                .map(OrderItem::getItemNo)
                .collect(Collectors.toList());
        return itemReader.findLockByItemNoIn(itemNumbers);
    }
}
