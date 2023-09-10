package kr.co.test.study.demo.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    SoldOutException("주문한 상품량이 재고량보다 큽니다."),

    ItemNotFoundException("주문한 상품을 찾을수 없습니다"),

    COMMON_INVALID_PARAMETER("요청한 값이 올바르지 않습니다.");

    private final String errorMessage;


}
