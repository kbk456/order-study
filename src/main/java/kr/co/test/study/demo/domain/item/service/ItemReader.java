package kr.co.test.study.demo.domain.item.service;

import kr.co.test.study.demo.domain.item.entity.Item;
import java.util.List;

public interface ItemReader {

    List<Item> findByItemNoIn(List<String> itemNumbers);

    List<Item> findLockByItemNoIn(List<String> itemNumbers);

    Item getItemById(String itemNo);

    List<Item> getItemAll();

    Item getItemBy(String itemNo);

}
