package xyc.maruko.expection;


import xyc.maruko.enums.RespEnum;

/**
 * 〈一句话功能简述〉<br>
 * 〈异常包装类〉
 *
 * @author maruko
 * @date 2021/7/7 10:49
 * @since 1.0.0
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 2897424491337847469L;

    /**
     * 错误码
     */
    private int errorCode;

    /**
     * 信息
     */
    private String message;

    /**
     * 错误信息详情
     */
    private String errorDetail;

    public BusinessException(String message) {
        super(message);
        this.errorCode = -1;
        this.setMessage(message);
    }

    public BusinessException(String message, String errorDetail) {
        super(message);
        this.errorCode = -1;
        this.setMessage(message);
        this.errorDetail = errorDetail;
    }

    public BusinessException(int errorCode, String message) {
        super(message);
        this.setErrorCode(errorCode);
        this.setMessage(message);
    }

    public BusinessException(RespEnum respEnum) {
        super(respEnum.message());
        this.setErrorCode(respEnum.code());
        this.setMessage(respEnum.message());
    }

    public BusinessException(Throwable cause) {
        super(cause);
        this.errorCode = RespEnum.FAILED.code();
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(int errorCode, String message, Throwable cause) {
        super(message, cause);
        this.setErrorCode(errorCode);
        this.setMessage(message);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }
}
