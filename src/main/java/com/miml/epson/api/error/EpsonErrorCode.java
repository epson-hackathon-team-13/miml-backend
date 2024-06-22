package com.miml.epson.api.error;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.EnumSet;

public enum EpsonErrorCode {
    PARSE_ERROR(400, "error.parse_error"),
    VALIDATION_ERROR(400, "error.validation_error"),
    INVALID_RESOURCE(400, "error.invalid_resource"),
    AUTHENTICATION_ERROR(401, "error.authentication_error"),
    ACCESS_TOKEN_VERIFICATION_FAILED(402, "error.access_token_verification_failed"),
    FORBIDDEN(403, "error.forbidden"),
    RATE_LIMIT_EXCEEDED(403, "error.rate_limit_exceeded"),
    NOT_FOUND(404, "error.not_found"),
    METHOD_NOT_ALLOWED(405, "error.method_not_allowed"),
    UNSUPPORTED_MEDIA_TYPE(415, "error.unsupported_media_type"),
    INTERNAL_SERVER_ERROR(500, "error.internal_server_error"),
    SERVICE_UNAVAILABLE(503, "error.service_unavailable");

    private final int statusCode;
    private String message;

    private MessageSource messageSource;

    private EpsonErrorCode(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return messageSource.getMessage(message, null, message, null);
    }

    public EpsonErrorCode setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
        return this;
    }

    @Component
    @AllArgsConstructor
    public static class EnumValuesInjectionService {
        private final MessageSource messageSource;

        @PostConstruct
        public void postConstruct() {
            for (EpsonErrorCode errorCode : EnumSet.allOf(EpsonErrorCode.class)) {
                errorCode.setMessageSource(messageSource);
            }
        }
    }
}
