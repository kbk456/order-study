package kr.co.test.study.demo.infrastructure.order;


import kr.co.test.study.demo.domain.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
