package com.maruko.enums;

import com.maruko.expection.BusinessException;

/**
 * 〈一句话功能简述〉<br>
 * 〈信息封装枚举〉
 *
 * @author maruko
 * @date 2021/7/7 10:49
 * @since 1.0.0
 */
public enum RespEnum {


    /**
     * 返回枚举
     */
    SUCCESS(0, "success"),
    FAILED(-1, "服务错误"),
    PARAMS_WRONG(-2, "参数错误或缺失");

    /**
     * 错误码
     */
    private int code;

    /**
     * 错误信息
     */
    private String message;

    public int code() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String message() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    RespEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    RespEnum(BusinessException e) {
        this.code = e.getErrorCode();
        this.message = e.getMessage();
    }
}
