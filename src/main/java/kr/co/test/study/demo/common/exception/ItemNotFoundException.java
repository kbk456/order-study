package kr.co.test.study.demo.common.exception;

import kr.co.test.study.demo.common.response.ErrorCode;

public class ItemNotFoundException extends BaseException {
    public ItemNotFoundException(){super(ErrorCode.SoldOutException);}
}
