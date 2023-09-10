package kr.co.test.study.demo.infrastructure.order;


import kr.co.test.study.demo.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findById(Long orderId);
}
