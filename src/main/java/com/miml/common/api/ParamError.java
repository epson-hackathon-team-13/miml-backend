package com.miml.common.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * parameter 검증에 통과하지 못한 필드가 담긴 클래스
 */
@Getter
@ToString
@AllArgsConstructor
public class ParamError {
    private String field;
    private Object value;
    private String reason;
}