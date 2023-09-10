package kr.co.test.study.demo.common.exception;

import kr.co.test.study.demo.common.response.ErrorCode;

public class EntityNotFoundException extends BaseException {

    public EntityNotFoundException() {
        super(ErrorCode.COMMON_INVALID_PARAMETER);
    }

}
