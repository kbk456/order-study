package kr.co.test.study.demo.interfaces.order;

import kr.co.test.study.demo.domain.order.dto.OrderCommand;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface OrderDtoMapper {

    OrderCommand.RegisterOrder of(OrderDto.RegisterOrderRequest request);

}
