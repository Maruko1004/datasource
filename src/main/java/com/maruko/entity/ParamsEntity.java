package com.maruko.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 〈一句话功能简述〉<br>
 * 〈参数详情〉
 *
 * @author maruko
 * @date 2021/7/6 17:07
 * @since 1.0.0
 */
@Data
public class ParamsEntity {


    /**
     * 序号
     */
    private Long id;

    /**
     * sql序号
     */
    @NotNull(message = "sql序号不能为空")
    private Long sqlId;

    /**
     * 参数名称
     */
    private String name;

    /**
     * 参数值
     */
    @NotNull(message = "参数值不能为空")
    private Object value;

    /**
     * 参数类型
     */
    private String type;

    public ParamsEntity() {
    }

    public ParamsEntity(Long sqlId, String name, Object value, String type) {
        this.sqlId = sqlId;
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public ParamsEntity(Long id, Long sqlId, String name, Object value, String type) {
        this.id = id;
        this.sqlId = sqlId;
        this.name = name;
        this.value = value;
        this.type = type;
    }
}
