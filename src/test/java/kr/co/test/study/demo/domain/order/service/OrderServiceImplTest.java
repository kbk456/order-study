package kr.co.test.study.demo.domain.order.service;

import kr.co.test.study.demo.common.exception.ItemNotFoundException;
import kr.co.test.study.demo.infrastructure.item.ItemRepository;
import kr.co.test.study.demo.interfaces.order.OrderApi;
import kr.co.test.study.demo.interfaces.order.OrderDto;
import kr.co.test.study.demo.common.exception.SoldOutException;
import kr.co.test.study.demo.domain.item.entity.Item;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class OrderServiceImplTest {


    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderApi orderApi;

    @Test
    void orderTest() throws SoldOutException, InterruptedException {
        final int orderCount = 46;
        final int buyCount = 1;
        final int soldOut = 0;
        final String itemNo = "768848";

        ExecutorService executorService = Executors.newFixedThreadPool(orderCount);
        CountDownLatch countDownLatch = new CountDownLatch(orderCount);

        for (int i = 0; i < orderCount; i++) {
            executorService.execute(() -> {

                // 주문 등록 요청 생성
                OrderDto.RegisterOrderRequest request = new OrderDto.RegisterOrderRequest();

                List<OrderDto.RegisterOrderItem> orderItemList = new ArrayList<>();

                OrderDto.RegisterOrderItem orderItem = new OrderDto.RegisterOrderItem();

                orderItem.setItemNo(itemNo);
                orderItem.setOrderCount(buyCount);
                orderItem.setItemPrice(1000L);
                orderItem.setItemName("test");
                orderItemList.add(orderItem);
                request.setUserId(100L);
                request.setOrderItemList(orderItemList);

                //주문 등록
                var orderId = orderApi.registerOrder(request);
                //결제
                orderApi.requestOrder(orderId);
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        Item findItem = itemRepository.findByItemNo(itemNo).orElseThrow(ItemNotFoundException::new);
        Assertions.assertThat(soldOut).isEqualTo(findItem.getItemStock());
    }
}