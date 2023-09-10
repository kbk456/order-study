package kr.co.test.study.demo.common.exception;


import kr.co.test.study.demo.common.response.ErrorCode;

public class InvalidParamException extends BaseException {

    public InvalidParamException(String errorCode) {
        super(ErrorCode.valueOf(errorCode));
    }

}
