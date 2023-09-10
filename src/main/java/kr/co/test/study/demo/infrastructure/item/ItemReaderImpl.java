package kr.co.test.study.demo.infrastructure.item;

import kr.co.test.study.demo.domain.item.entity.Item;
import kr.co.test.study.demo.domain.item.service.ItemReader;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ItemReaderImpl implements ItemReader {

    private final ItemRepository itemRepository;

    @Override
    public List<Item> findByItemNoIn(List<String> itemNumbers) {
        return itemRepository.findByItemNoInOrderByItemNo(itemNumbers);
    }

    @Override
    public List<Item> findLockByItemNoIn(List<String> itemNumbers) {
        return itemRepository.findLockByItemNoInOrderByItemNo(itemNumbers);
    }

    @Override
    public Item getItemById(String itemNo) {
        return itemRepository.findByItemNo(itemNo)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Item> getItemAll() {
        return itemRepository.findAllByOrderByItemNoDesc();
    }

    @Override
    public Item getItemBy(String itemNo) {
        return itemRepository.findByItemNo(itemNo)
                .orElseThrow(EntityNotFoundException::new);
    }

}
