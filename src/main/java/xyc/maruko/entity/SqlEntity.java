package xyc.maruko.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 〈一句话功能简述〉<br>
 * 〈sql实体类〉
 *
 * @author maruko
 * @date 2021/7/6 18:02
 * @since 1.0.0
 */
@Data
public class SqlEntity {
    /**
     * 序号
     */
    private Long id;

    /**
     * 数据库编号
     */
    @NotNull(message = "数据库编号不能为空")
    private Long jdbcId;

    /**
     * 执行sql
     */
    @NotNull(message = "执行sql不能为空")
    private String sql;

    public SqlEntity() {
    }

    public SqlEntity(Long jdbcId, String sql) {
        this.jdbcId = jdbcId;
        this.sql = sql;
    }

    public SqlEntity(Long id, Long jdbcId, String sql) {
        this.id = id;
        this.jdbcId = jdbcId;
        this.sql = sql;
    }
}
