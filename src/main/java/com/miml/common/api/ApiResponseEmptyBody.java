package com.miml.common.api;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * ApiResponse Body 가 없는 경우
 * null 대신 사용되는 객s체.
 * */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ApiResponseEmptyBody {

    private static ApiResponseEmptyBody emptyBody;

    private ApiResponseEmptyBody() {

    }

    public static ApiResponseEmptyBody getInstance() {
        if (emptyBody == null) {
            emptyBody = new ApiResponseEmptyBody();
        }

        return emptyBody;
    }
}
