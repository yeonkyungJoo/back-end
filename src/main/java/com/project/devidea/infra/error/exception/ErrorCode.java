package com.project.devidea.infra.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "Invalid Input Value"),
    METHOD_NOT_ALLOWED(400," Invalid Input Value"),
    UNAUTHORIZED(401," UnAuthorized "),
    INTERNAL_SERVER_ERROR(500, "Server Error"),
    INVALID_TYPE_VALUE(400, " Invalid Type Value"),
    HANDLE_ACCESS_DENIED(403, "Access is Denied"),
    ENTITY_NOT_FOUND(400, " Entity Not Found"),

//    Account 관련 에러코드
    ACCOUNT_ERROR(400, "Invalid Input Value From Account"),

    ENTITY_ALREADY_EXIST(400, "Entity Already Exist");

//    // Member
//    EMAIL_DUPLICATION(400,"Email is Duplication"),
//    LOGIN_INPUT_INVALID(400,"Login input is invalid"),
//
//    // Coupon
//    COUPON_ALREADY_USE(400,"Coupon was already used"),
//    COUPON_EXPIRE(400,"Coupon was already expired");

    private final int status;
    private String code;

    ErrorCode(final int status, final String code) {
        this.status = status;
        this.code = code;
    }


}
