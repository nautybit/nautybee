package com.nautybit.nautybee.common.result;

/**
 * Created by Minutch on 15/6/26.
 */
public enum ErrorCode {

    UNKNOW_ERROR("-999", "未知异常"),
    /************************************************
     * 接口安全验证失败(系统级错误码)
     ************************************************/
    ERROR_SECURITY_CODE("-1", "接口安全认证失败！");

    private String code;
    private String message;

    private ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
