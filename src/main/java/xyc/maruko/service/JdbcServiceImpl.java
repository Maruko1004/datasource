package xyc.maruko.service;

import xyc.maruko.entity.BaseJdbcEntity;
import xyc.maruko.entity.JdbcEntity;
import xyc.maruko.enums.RespEnum;
import xyc.maruko.utils.JdbcUtil;
import xyc.maruko.vo.ResponseVo;

import java.util.ArrayList;
import java.util.Arrays;
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

public class JdbcServiceImpl {

    public static final String INSERT_SQL = "INSERT INTO `jdbc_info`(`drive_class`, `url`, `user_name`, `password`) VALUES (?, ?, ?, ?)";

    public static final String QUERY_BY_ID_SQL = "select * from jdbc_info where id=?";

    public static final String ID = "id";

    public static final String DRIVER_CLASS_TYPE = "drive_class";

    public static final String URL = "url";

    public static final String USER_NAME = "user_name";

    public static final String PASSWORD = "password";

    /**
     * 添加jdbc连接信息
     *
     * @param baseJdbcEntity 初始化数据源
     * @param jdbcEntity     数据库信息
     * @return
     */
    public static ResponseVo addJdbc(BaseJdbcEntity baseJdbcEntity, JdbcEntity jdbcEntity) {
        baseJdbcEntity = JdbcUtil.initBaseJdbc(baseJdbcEntity);
        JdbcUtil.executeInsert(baseJdbcEntity, INSERT_SQL, jdbcToList(jdbcEntity));
        return new ResponseVo(RespEnum.SUCCESS);
    }

    /**
     * 根据id查询数据库信息
     *
     * @param baseJdbcEntity
     * @param id
     * @return
     */
    public static JdbcEntity queryJdbcInfo(BaseJdbcEntity baseJdbcEntity, Long id) {
        baseJdbcEntity = JdbcUtil.initBaseJdbc(baseJdbcEntity);
        List<Map<String, Object>> mapList = JdbcUtil.executeQuery(baseJdbcEntity, QUERY_BY_ID_SQL, Arrays.asList(id));
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
     * @param map
     * @return
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
     * @param jdbcEntity
     * @return
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
