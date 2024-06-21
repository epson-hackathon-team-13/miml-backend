package com.miml.common.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


/**
 * Api 정상응답의 경우 ApiResponse<T>, 에러응답의 경우 ApiErrResponse 사용
 * Swagger상에 Api 응답을 규격화 하기 위해 ApiResponse, ApiErrResponse 는 같은 변수 구조를 가짐
 * 정상인경우 ApiResponse.data 가 사용되고, 에러인경우 ApiErrResponse.paramErrors 등이 사용됨.
 */
@Getter
@ToString
public class ApiResponse<T> {

    @Schema(description = "타임스탬프")
    private final String timestamp = LocalDateTime.now().toString();

    @Schema(description = "트랜잭션 아이디")
    private final UUID txId = UUID.randomUUID();

    @Schema(description = "에러")
    private String error;

    @Schema(description = "메시지")
    private String message;

    @Schema(description = "데이터")
    private T data;

    // 에러응답과 형식을 맞추기 위한 변수
    @Schema(description = "입력값 에러")
    private List<ParamError> paramErrors;

    /*
     * 생성자를 통해 해당 객체를 생성할 경우 data 의 제너릭 타입과 정적 메소드를 통해 삽입한 객체의 타입이 다를 수 있기 때문에 private 생성자로 선언
     * ApiResponse apiResponse = new ApiResponse<String>(); apiResponse.toOkResponseEntity(123123); //
     * bad
     */
    private ApiResponse(String error, String message, T data) {
        this.error = error;
        this.message = message;
        this.data = data;
    }

    // 정상 리턴 void
    public static ResponseEntity<ApiResponse<ApiResponseEmptyBody>> toOkResponseEntity() {
        return toResponseEntity(ErrorCode.OK, ApiResponseEmptyBody.getInstance());
    }

    // 정상 리턴 data
    public static <T> ResponseEntity<ApiResponse<T>> toOkResponseEntity(T data) {
        return toResponseEntity(ErrorCode.OK, data);
    }

    // 리턴 공통
    public static <T> ResponseEntity<ApiResponse<T>> toResponseEntity(ErrorCode errorCode, T data) {

        ApiResponse<T> apiResponse =
                new ApiResponse<>(errorCode.getErrCd(), errorCode.getErrMsg(), data);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    /**
     * Json 형식 정상 응답
     */
    public static void jsonOkResponse(HttpServletResponse response, HashMap<String, Object> dataMap)
            throws IOException {
        ResponseEntity<ApiResponse<HashMap<String, Object>>> responseEntity =
                ApiResponse.toOkResponseEntity(dataMap);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());

        try (OutputStream os = response.getOutputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(os, responseEntity.getBody());
            os.flush();
        }
    }
}