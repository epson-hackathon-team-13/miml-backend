package com.miml.common.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Api 정상응답의 경우 ApiResponse<T>, 에러응답의 경우 ApiErrResponse 사용
 * Swagger상에 Api 응답을 규격화 하기 위해 ApiResponse, ApiErrResponse 는 같은 변수 구조를 가짐
 * 정상인경우 ApiResponse.data 가 사용되고, 에러인경우 ApiErrResponse.paramErrors 등이 사용됨.
 */
@Getter
@ToString
public class ApiErrorResponse {

    @Schema(description = "타임스탬프")
    private final String timestamp = LocalDateTime.now().toString();

    @Schema(description = "트랜잭션 아이디")
    private final UUID txId = UUID.randomUUID();

    @Schema(description = "에러")
    private String error;

    @Schema(description = "메시지")
    private String message;

    // 정상응답과 형식을 맞추기 위한 변수
    private Object data = null;

    @Schema(description = "입력값 에러")
    private List<ParamError> paramErrors;

    public ApiErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public ApiErrorResponse(String error, String message, List<ParamError> paramErrors) {
        this.error = error;
        this.message = message;
        this.paramErrors = paramErrors;
    }

    // 오류 코드 리턴(정의된 오류 메시지)
    public static ResponseEntity<ApiErrorResponse> toErrResponseEntity(ErrorCode errorCode) {
        return toResponseEntity(errorCode, null);
    }

    // 오류 메세지 리턴 (코드에 정의된 메시지 우선적용)
    public static ResponseEntity<ApiErrorResponse> toErrResponseEntity(ErrorCode errorCode,
                                                                     String msg) {
        if (errorCode != null) { // 에러코드코드에 매칭되는 메세지 리턴
            return toErrResponseEntity(errorCode);
        } else { // 사용자 정의 에러메세지 리턴
            ApiErrorResponse apiResponse = new ApiErrorResponse(ErrorCode.MSG.getErrCd(), msg);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        }
    }

    // 오류 메세지 리턴 (코드 + 메시지)
    public static ResponseEntity<ApiErrorResponse> toErrResponseEntityCodeNmsg(ErrorCode errorCode,
                                                                             String msg) {
        ApiErrorResponse apiResponse = new ApiErrorResponse(errorCode.getErrCd(), msg);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    // 리턴 공통
    public static ResponseEntity<ApiErrorResponse> toResponseEntity(ErrorCode errorCode,
                                                                  List<ParamError> paramErrors) {
        HttpStatus httpStatus = HttpStatus.OK;
        // HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        if (ErrorCode.FORBIDDEN.equals(errorCode)) {
            httpStatus = HttpStatus.FORBIDDEN;
        }
        if (ErrorCode.UNAUTHORIZED.equals(errorCode) || ErrorCode.BADCREDENTIAL.equals(errorCode)
                || ErrorCode.DISABLED.equals(errorCode) || ErrorCode.CREDENTIALEXPIRED.equals(errorCode)
                || ErrorCode.LOCKED.equals(errorCode) || ErrorCode.ACCOUNTEXPIRED.equals(errorCode)) {
            httpStatus = HttpStatus.UNAUTHORIZED;
        }

        ApiErrorResponse apiResponse =
                new ApiErrorResponse(errorCode.getErrCd(), errorCode.getErrMsg(), paramErrors);
        return ResponseEntity.status(httpStatus).body(apiResponse);
    }

    // 사용자 정의 메세지를 리턴하면서 상태코드까지 제어
    public static ResponseEntity<ApiErrorResponse> toErrResponseEntity(HttpStatusCode statusCode,
                                                                     String msg) {
        ApiErrorResponse apiResponse = new ApiErrorResponse(ErrorCode.MSG.getErrCd(), msg);
        return ResponseEntity.status(statusCode).body(apiResponse);
    }

    /**
     * Json 형식 Error 응답
     */
    public static void jsonErrorResponse(HttpServletResponse response, ErrorCode errorCode,
                                         int statuscode)
            throws IOException {
        ResponseEntity<ApiErrorResponse> responseEntity = ApiErrorResponse.toErrResponseEntity(errorCode);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(statuscode);

        try (OutputStream os = response.getOutputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(os, responseEntity.getBody());
            os.flush();
        }
    }

}
