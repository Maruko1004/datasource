package xyc.maruko.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import xyc.maruko.dto.PageBaseDTO;
import xyc.maruko.enums.RespEnum;
import xyc.maruko.expection.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈返回包装类〉
 *
 * @author maruko
 * @date 2021/7/7 10:49
 * @since 1.0.0
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseVo<T> implements Serializable {

    /**
     * 错误码
     */
    private int code;

    /**
     * 错误信息
     */
    private String error;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 错误详情
     */
    private String errorDetail;

    private PageBaseDTO page;

    public ResponseVo(RespEnum respEnum) {
        this.code = respEnum.code();
        this.error = respEnum.message();
    }

    public ResponseVo(String error, String errorDetail) {
        this.code = RespEnum.FAILED.code();
        this.error = error;
        this.errorDetail = errorDetail;
    }

    public ResponseVo(int code, String error, String errorDetail) {
        this.code = code;
        this.error = error;
        this.errorDetail = errorDetail;
    }

    public ResponseVo(RespEnum respEnum, T data) {
        this.code = respEnum.code();
        this.error = respEnum.message();
        this.data = data;
    }

    public ResponseVo(int errorCode, String message) {
        this.code = errorCode;
        this.error = message;
    }

    public ResponseVo(int errorCode, String message, T data) {
        this.code = errorCode;
        this.error = message;
        this.data = data;
    }

    public ResponseVo(RespEnum respEnum, String message, T data) {
        this.code = respEnum.code();
        this.error = message;
        this.data = data;
    }

    public ResponseVo(IPage<T> ipage) {
        this.code = RespEnum.SUCCESS.code();
        this.error = RespEnum.SUCCESS.message();
        this.data = (T) ipage.getRecords();
        this.page = PageBaseDTO.builder().current(ipage.getCurrent())
                .size(ipage.getSize()).total(ipage.getTotal()).build();
    }

    public ResponseVo(Page<T> ipage) {
        this.code = RespEnum.SUCCESS.code();
        this.error = RespEnum.SUCCESS.message();
        this.data = (T) ipage.getRecords();
        this.page = PageBaseDTO.builder().current(ipage.getCurrent())
                .size(ipage.getSize()).total(ipage.getTotal()).build();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public PageBaseDTO getPage() {
        return page;
    }

    public void setPage(PageBaseDTO page) {
        this.page = page;
    }

    public String getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }

    public T queryData() {
        if (this.getCode() != RespEnum.SUCCESS.code()) {
            throw new BusinessException(this.getCode(), this.getError());
        } else {
            return data;
        }
    }
}
