package xyc.maruko.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 〈一句话功能简述〉<br>
 * 〈jdbc连接实体类〉
 *
 * @author maruko
 * @date 2021/7/6 16:12
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class JdbcEntity extends BaseJdbcEntity {

    public JdbcEntity() {
    }

    public JdbcEntity(String driverClassType, String url, String userName, String password) {
        this.driverClassType = driverClassType;
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    public JdbcEntity(Long id, String driverClassType, String url, String userName, String password) {
        super(id, driverClassType, url, userName, password);
    }
}
