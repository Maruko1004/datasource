package xyc.maruko.utils;

import xyc.maruko.entity.BaseJdbcEntity;
import xyc.maruko.entity.JdbcEntity;
import xyc.maruko.enums.RespEnum;
import xyc.maruko.vo.ResponseVo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈数据库信息录入实现层〉
 *
 * @author maruko
 * @date 2021/7/7 10:45
 * @since 1.0.0
 */

public class JdbcUtil {

    private static final String INSERT_SQL = "INSERT INTO `jdbc_info`(`drive_class`, `url`, `user_name`, `password`) VALUES (?, ?, ?, ?)";

    private static final String QUERY_BY_ID_SQL = "select * from jdbc_info where id=?";

    private static final String ID = "id";

    private static final String DRIVER_CLASS_TYPE = "drive_class";

    private static final String URL = "url";

    private static final String USER_NAME = "user_name";

    private static final String PASSWORD = "password";

    /**
     * 添加jdbc连接信息
     *
     * @param baseJdbcEntity 初始化数据源
     * @param jdbcEntity     数据库信息
     * @return ResponseVo
     */
    public static ResponseVo addJdbc(BaseJdbcEntity baseJdbcEntity, JdbcEntity jdbcEntity) {
        baseJdbcEntity = BaseJdbcUtil.initBaseJdbc(baseJdbcEntity);
        BaseJdbcUtil.executeInsert(baseJdbcEntity, INSERT_SQL, jdbcToList(jdbcEntity));
        return new ResponseVo(RespEnum.SUCCESS);
    }

    /**
     * 根据id查询数据库信息
     *
     * @param baseJdbcEntity 初始化数据源
     * @param id             编号
     * @return JdbcEntity
     */
    protected static JdbcEntity queryJdbcInfo(BaseJdbcEntity baseJdbcEntity, Long id) {
        baseJdbcEntity = BaseJdbcUtil.initBaseJdbc(baseJdbcEntity);
        List<Map<String, Object>> mapList = BaseJdbcUtil.executeQuery(baseJdbcEntity, QUERY_BY_ID_SQL, Collections.singletonList(id));
        if (null != mapList && mapList.size() > 0) {
            //数据库连接一一对应关系，只取1
            Map<String, Object> map = mapList.get(0);
            return mapToJdbcEntity(map);
        }
        return null;
    }

    /**
     * 将map转换为JdbcEntity
     *
     * @param map map
     * @return JdbcEntity
     */
    private static JdbcEntity mapToJdbcEntity(Map<String, Object> map) {
        JdbcEntity jdbcEntity = new JdbcEntity();
        jdbcEntity.setId(Long.parseLong(map.get(ID).toString()));
        jdbcEntity.setDriverClassType(map.get(DRIVER_CLASS_TYPE).toString());
        jdbcEntity.setUrl(map.get(URL).toString());
        jdbcEntity.setUserName(map.get(USER_NAME).toString());
        jdbcEntity.setPassword(map.get(PASSWORD).toString());
        return jdbcEntity;
    }

    /**
     * 将实体转换为list
     *
     * @param jdbcEntity 数据库实体
     * @return List<Object>
     */
    private static List<Object> jdbcToList(JdbcEntity jdbcEntity) {
        if (null == jdbcEntity) {
            return null;
        }
        List<Object> list = new ArrayList<>();
        list.add(jdbcEntity.getDriverClassType());
        list.add(jdbcEntity.getUrl());
        list.add(jdbcEntity.getUserName());
        list.add(jdbcEntity.getPassword());
        return list;
    }
}
