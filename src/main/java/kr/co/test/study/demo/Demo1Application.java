package kr.co.test.study.demo;

import kr.co.test.study.demo.domain.order.dto.OrderInfo;
import kr.co.test.study.demo.interfaces.item.ItemApi;
import kr.co.test.study.demo.interfaces.order.OrderApi;
import kr.co.test.study.demo.interfaces.order.OrderDto;
import kr.co.test.study.demo.common.exception.SoldOutException;
import kr.co.test.study.demo.domain.item.dto.ItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
public class Demo1Application {


    @Autowired
    private ItemApi itemApi;

    @Autowired
    private OrderApi orderApi;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Demo1Application.class, args);
        Demo1Application application = context.getBean(Demo1Application.class);
        application.orderScanner();
    }

    private void orderScanner() {
        Scanner sc = new Scanner(System.in);
        String initInput;

        do {
            System.out.print("입력(o[order]:주문, q[quit]:종료) : ");
            initInput = sc.nextLine().trim();

            if (initInput.equals("o")) {
                //주문 요청값 입력
                var orderItemRequest = processOrder(sc);

                if(orderItemRequest.size() == 0){
                    System.out.println("주문이 등록되지 않았습니다.");
                    break;
                }

                try {
                    //주문 아이템 조회
                    List<ItemDto> itemInfoList = itemApi.retrieveItemInfoList(orderItemRequest);

                    // itemNo를 기준으로 매핑할 Map 생성
                    Map<String, Integer> itemNoToOrderCountMap = mapOrderItemRequestToMap(orderItemRequest);

                    // 주문 등록 요청 생성
                    OrderDto.RegisterOrderRequest request = createOrderRequest(itemNoToOrderCountMap, itemInfoList);

                    //주문 등록
                    Long orderId = orderApi.registerOrder(request);
                    //결제
                    orderApi.requestOrder(orderId);
                    //주문완료 조회
                    OrderInfo.Main orderComplite = orderApi.retrieveOrder(orderId);
                    //결과 출력
                    displayOrderInfo(orderComplite);

                }catch (SoldOutException e){
                    System.out.println(e.getErrorCode() + " " + e.getMessage());
                }

            } else if (initInput.equals("q")) {
                System.out.println("고객님의 주문 감사합니다");
            }
        } while (initInput.equals("o"));

        sc.close();
    }

    private OrderDto.RegisterOrderRequest createOrderRequest(Map<String, Integer> itemNoToOrderCountMap, List<ItemDto> itemInfoList) {
        OrderDto.RegisterOrderRequest request = new OrderDto.RegisterOrderRequest();
        request.setUserId(1L);
        setItemOrderCounts(itemInfoList, itemNoToOrderCountMap);
        request.setOrderItemList(mapItemDtosToRegisterOrderItems(itemInfoList));
        return request;
    }

    // 주문 내역 조회 및 출력
    private void displayOrderInfo(OrderInfo.Main order) {
        System.out.println("주문 내역:");
        System.out.println("-----------------------------------------------");

        List<OrderInfo.OrderItem> orderItemList = order.getOrderItemList();

        for (OrderInfo.OrderItem orderItem : orderItemList) {
            displayOrderItem(orderItem);
        }

        System.out.println("-----------------------------------------------");
        System.out.println("주문금액: " + formatAmount(order.getOrderAmount()) + "원");
        System.out.println("-----------------------------------------------");
        System.out.println("지불금액: " + formatAmount(order.getTotalAmount()) + "원");
        System.out.println("-----------------------------------------------");
    }

    // 주문 항목 출력
    private void displayOrderItem(OrderInfo.OrderItem orderItem) {
        System.out.println(orderItem.getItemName() + " - " + orderItem.getOrderCount() + "개");
    }

    // 금액 포맷팅
    private String formatAmount(long amount) {
        return String.format("%,d", amount);
    }

    // 주문 아이템 요청을 Map으로 변환하는 메서드
    private Map<String, Integer> mapOrderItemRequestToMap(List<OrderDto.OrderItemRequest> orderItemRequest) {
        return orderItemRequest.stream()
                .collect(Collectors.toMap(OrderDto.OrderItemRequest::getItemNo, OrderDto.OrderItemRequest::getOrderCount));
    }
    // ItemDto 리스트의 주문 수량 세팅 메서드
    private void setItemOrderCounts(List<ItemDto> itemInfoList, Map<String, Integer> itemNoToOrderCountMap) {
        itemInfoList.forEach(itemDto -> itemDto.setItemOrderCount(itemNoToOrderCountMap.getOrDefault(itemDto.getItemNo(), 0)));
    }

    private List<OrderDto.RegisterOrderItem> mapItemDtosToRegisterOrderItems(List<ItemDto> itemDtos) {
        return itemDtos.stream().map(itemDto -> {
            OrderDto.RegisterOrderItem orderItem = new OrderDto.RegisterOrderItem();
            orderItem.setOrderCount(itemDto.getItemOrderCount());
            orderItem.setItemNo(itemDto.getItemNo().toString());
            orderItem.setItemName(itemDto.getItemName());
            orderItem.setItemPrice(itemDto.getItemPrice());
            return orderItem;
        }).collect(Collectors.toList());
    }

    private List<OrderDto.OrderItemRequest> processOrder(Scanner scanner) {
        List<OrderDto.OrderItemRequest> orderItems = new ArrayList<>();

        //상품 전체 조회
        List<ItemDto> itemDtos = itemApi.retrieveItemAll();
        //출력
        printItemAll(itemDtos);

        while (true) {
            OrderDto.OrderItemRequest orderItem = readOrderItem(scanner);
            if (orderItem == null) {
                break;
            }
            orderItems.add(orderItem);
        }

        printOrderItems(orderItems);

        return orderItems;
    }

    private OrderDto.OrderItemRequest readOrderItem(Scanner scanner) {
        System.out.print("상품번호: ");
        String itemNo = scanner.nextLine();

        System.out.print("상품수량: ");
        String quantity = scanner.nextLine();

        if (itemNo.isBlank() || quantity.isBlank()) {
            return null;
        }

        return createOrderItem(itemNo, Integer.valueOf(quantity));
    }

    private OrderDto.OrderItemRequest createOrderItem(String itemNo, int quantity) {
        OrderDto.OrderItemRequest orderItem = new OrderDto.OrderItemRequest();
        orderItem.setItemNo(itemNo);
        orderItem.setOrderCount(quantity);
        return orderItem;
    }

    private void printOrderItems(List<OrderDto.OrderItemRequest> orderItems) {
        for (int i = 0; i < orderItems.size(); i++) {
            System.out.println("상품번호: " + orderItems.get(i).getItemNo());
            System.out.println("상품갯수: " + orderItems.get(i).getOrderCount());
        }
    }

    private static void printItemAll(List<ItemDto> itemDtos) {
        System.out.println("상품번호          상품명         판매가격            재고수");
        for (ItemDto itemDto : itemDtos) {
            System.out.print(itemDto.getItemNo());
            System.out.print("          " + itemDto.getItemName());
            System.out.print("          " + itemDto.getItemPrice());
            System.out.print("          " + itemDto.getItemStock());
            System.out.println();
        }
    }

}
