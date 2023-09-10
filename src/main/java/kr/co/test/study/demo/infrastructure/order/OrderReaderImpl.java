package kr.co.test.study.demo.infrastructure.order;

import kr.co.test.study.demo.common.exception.EntityNotFoundException;
import kr.co.test.study.demo.domain.order.entity.Order;
import kr.co.test.study.demo.domain.order.service.OrderReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderReaderImpl implements OrderReader {

    private final OrderRepository orderRepository;

    @Override
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);
    }
}
