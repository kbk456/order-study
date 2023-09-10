package kr.co.test.study.demo.domain.mapper;

import kr.co.test.study.demo.domain.item.dto.ItemDto;
import kr.co.test.study.demo.domain.item.entity.Item;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemMapper extends EntityMapper<ItemDto, Item> {}
