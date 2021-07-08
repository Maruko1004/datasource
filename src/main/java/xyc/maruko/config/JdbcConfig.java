package xyc.maruko.config;


import freemarker.template.utility.ClassUtil;
import xyc.maruko.entity.BaseJdbcEntity;
import xyc.maruko.enums.JdbcEnum;
import xyc.maruko.expection.BusinessException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * 〈一句话功能简述〉<br>
 * 〈数据库连接配置〉
 *
 * @author maruko
 * @date 2021/7/6 16:30
 * @since 1.0.0
 */
public class JdbcConfig {

    /**
     * @param baseJdbcEntity 数据库配置信息
     * @return Connection
     */
    public static Connection getConnection(BaseJdbcEntity baseJdbcEntity) {
        try {
            //判断是否包含对应驱动
            String driverClassType = baseJdbcEntity.getDriverClassType();
            String driverClass = JdbcEnum.getDriverClassByType(driverClassType);
            if (null == driverClass || "".equals(driverClass)) {
                throw new BusinessException("暂不支持该数据库驱动，请联系管理员");
            }
            ClassUtil.forName(driverClass);
            Properties info = new Properties() {{
                setProperty("user", baseJdbcEntity.getUserName());
                setProperty("password", baseJdbcEntity.getPassword());
            }};
            return DriverManager.getConnection(baseJdbcEntity.getUrl(), info);
        } catch (Exception e) {
            throw new BusinessException("数据库连接失败，账号或密码错误");
        }
    }
}
