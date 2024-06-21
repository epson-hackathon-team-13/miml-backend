package com.miml.common.api;
import java.util.EnumSet;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

public enum ErrorCode {
    //messages.properties 파일에 정의되어 있음

    //@formatter:off
    OK("OK", "errCd.OK"),
    PARAM_ERR("PARAM_ERR", "errCd.PARAM_ERR"),
    BADREQUEST("BADREQUEST", "errCd.PARAM_ERR"),

    UNAUTHORIZED("UNAUTHORIZED", "errCd.UNAUTHORIZED"),
    FORBIDDEN("FORBIDDEN", "errCd.FORBIDDEN"),
    CSRF("CSRF", "errCd.CSRF"),

    BADCREDENTIAL("BADCREDENTIAL", "errCd.BADCREDENTIAL"),
    LOCKED("LOCKED", "errCd.LOCKED"),
    DISABLED("DISABLED", "errCd.DISABLED"),
    ACCOUNTEXPIRED("ACCOUNTEXPIRED", "errCd.ACCOUNTEXPIRED"),
    CREDENTIALEXPIRED("CREDENTIALEXPIRED", "errCd.CREDENTIALEXPIRED"),

    USERNOTFOUND("USERNOTFOUND", "errCd.USERNOTFOUND"),
    USERALREADYEXIST("USERALREADYEXIST", "errCd.USERALREADYEXIST"),
    PHONEUNAUTHORIZED("PHONEUNAUTHORIZED", "errCd.PHONEUNAUTHORIZED"),
    MSG("MSG", "전문이나 api에서 전달된 메세지를 그대로 전달할때 사용"),

    CMMN_ERR("ERR", "errCd.ERR"),

    DB_ERR("E002", "Database Error"),
    API_ERR("E003", "Api Error"),
    SEC_ERR("E004", "Security Error"),
    ;
    //@formatter:on

    private String errCd;
    private String errMsg;


    private ErrorCode(String errCd, String errMsg) {
        this.errCd = errCd;
        this.errMsg = errMsg;
    }

    public String getErrCd() {
        return errCd;
    }

    public String getErrMsg() {
        return messageSource.getMessage(errMsg, null, errMsg, null);
    }

    private MessageSource messageSource;

    public ErrorCode setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
        return this;
    }

    @Component
    public static class EnumValuesInjectionService {

        private final MessageSource messageSource;
        
        public EnumValuesInjectionService(MessageSource messageSource) {
			this.messageSource = messageSource;
		}

		@PostConstruct
        public void postConstruct() {

            for (ErrorCode errCd : EnumSet.allOf(ErrorCode.class)) {
                errCd.setMessageSource(messageSource);
            }
        }
    }
}