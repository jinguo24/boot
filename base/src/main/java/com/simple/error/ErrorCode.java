package com.simple.error;

import java.text.MessageFormat;

/**
 * @author chenkx
 * @date 2017-12-28.
 */
public enum ErrorCode{

    /**
     * 系统级别
     * 1000-1999
     */
    SERVER_ERROR(1000, "服务器请求异常"),
    PARAMETER_ERROR(1001, "参数不合法"),
    PARAMETER_NOT_NULL(1002, "参数不能为空"),
    PARAMETER_TYPE_ERROR(1003, "参数类型异常"),
    PARAMETER_CAST_ERROR(1004, "参数转换异常"),
    PARAMETER_EMPTY_ERROR(1005, "参数转换异常"),
    BEAN_EMPTY_PROPERTY_ERROR(1006, "实体转换异常"),
    MEDIATYPE_NOT_SUPPORT(1007, "不支持的媒体类型"),
    NULLPOINTER_ERROR(1008, "空指针异常"),
    METHOD_NOT_SUPPORT(1009, "不支持的请求类型"),
    CLASSCAST_ERROR(1010, "类型转换异常"),
    NUMBER_FORMAT_ERROR(1011, "数字转化异常"),
    EXTEND_JSON_ERROR(1012, "JSON格式异常"),
    REPEAT_SUBMIT_EXCEPTION(1013, "请勿重复操作"),

    LOGIN_DENIED(1013, "登录失败"),
    TOKEN_INVALID(1014, "TOKEN无效"),


    /**
     * 业务级别
     */
    /**
     * 用户2000-2099
     */
    USER_IS_EMPTY(2000, "用户不存在"),
    PASSWORD_ERROR(2001, "密码错误"),
    LOGIN_USER_OR_PWD_ERROR(2002, "用户名或密码错误"),
    USER_IS_LOCKED(2003, "用户已经被锁定不能登录，请与管理员联系");



    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage(Object... param) {
        if (param.length == 0) {
            return message;
        }
        return MessageFormat.format(message, param);
    }

    public static ErrorCode getByCode(int code) {
        ErrorCode[] values = ErrorCode.values();
        for (ErrorCode value : values) {
            if (value.getCode() == code) {
                return value;
            }

        }
        return null;
    }
}
