package kr.co.test.study.demo.common.exception;

import kr.co.test.study.demo.common.response.ErrorCode;

public class SoldOutException extends BaseException {
    public SoldOutException(){super(ErrorCode.SoldOutException);}
}
