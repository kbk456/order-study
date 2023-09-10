package kr.co.test.study.demo.infrastructure.item;

import kr.co.test.study.demo.domain.item.entity.Item;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item,Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="3000")})
    List<Item> findLockByItemNoInOrderByItemNo(List<String> itemNoList);

    List<Item> findByItemNoInOrderByItemNo(List<String> itemNoList);

    Optional<Item> findByItemNo(String itemNo);

    List<Item> findAllByOrderByItemNoDesc();
}
