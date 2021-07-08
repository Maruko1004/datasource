package xyc.maruko.service;

import xyc.maruko.entity.BaseJdbcEntity;
import xyc.maruko.entity.JdbcEntity;
import xyc.maruko.entity.SqlEntity;
import xyc.maruko.enums.RespEnum;
import xyc.maruko.utils.JdbcUtil;
import xyc.maruko.vo.ResponseVo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈SQL信息录入实现层〉
 *
 * @author maruko
 * @date 2021/7/7 10:46
 * @since 1.0.0
 */

public class SqlServiceImpl {

    public static final String INSERT_SQL = "INSERT INTO `sql_info`( `jdbc_id`, `sql`) VALUES (?, ?)";

    public static final String EXECUTE_SQL = "select * from sql_info where id=?";

    public static final String ID = "id";

    public static final String JDBC_ID = "jdbc_id";

    public static final String SQL = "sql";

    /**
     * 添加sql信息
     *
     * @param baseJdbcEntity 初始化数据源
     * @param sqlEntity      SQL实体
     * @return
     */
    public static ResponseVo addSql(BaseJdbcEntity baseJdbcEntity, SqlEntity sqlEntity) {
        baseJdbcEntity = JdbcUtil.initBaseJdbc(baseJdbcEntity);
        JdbcUtil.executeInsert(baseJdbcEntity, INSERT_SQL, sqlToList(sqlEntity));
        return new ResponseVo(RespEnum.SUCCESS);
    }

    /**
     * 执行SQL参数需要在数据库配置
     *
     * @param baseJdbcEntity 初始化数据源
     * @param id             编号
     * @param params         参数
     * @return
     */
    public static ResponseVo executeSql(BaseJdbcEntity baseJdbcEntity, Long id, List<Object> params) {
        List<Map<String, Object>> list = getResult(baseJdbcEntity, id);

        //查询出来的数据只会有一条
        if (null != list && list.size() > 0) {
            Map<String, Object> map = list.get(0);
            SqlEntity sqlEntity = mapToSqlEntity(map);
            //获取数据库id
            Long jdbcId = sqlEntity.getJdbcId();
            //根据jdbcId查询jdbc连接信息
            JdbcEntity jdbcEntity = JdbcServiceImpl.queryJdbcInfo(baseJdbcEntity, jdbcId);
            //根据id查询参数信息 如果有传参 用自己传的参数 没有去数据库查询是否有配置参数
            if (null == params || params.size() == 0) {
                params = ParamsServiceImpl.queryParamsInfo(baseJdbcEntity, id);
            }
            List<Map<String, Object>> mapList = JdbcUtil.executeQuery(jdbcEntity, sqlEntity.getSql(), params);
            return new ResponseVo(RespEnum.SUCCESS, mapList);
        }

        return new ResponseVo(RespEnum.SUCCESS);
    }

    /**
     * 查询SQL信息
     *
     * @param baseJdbcEntity
     * @param id
     * @return
     */
    private static List<Map<String, Object>> getResult(BaseJdbcEntity baseJdbcEntity, Long id) {
        baseJdbcEntity = JdbcUtil.initBaseJdbc(baseJdbcEntity);
        return JdbcUtil.executeQuery(baseJdbcEntity, EXECUTE_SQL, Arrays.asList(id));
    }

    /**
     * map转换为实体
     *
     * @param map
     * @return
     */
    private static SqlEntity mapToSqlEntity(Map<String, Object> map) {
        SqlEntity sqlEntity = new SqlEntity();
        sqlEntity.setId(Long.parseLong(map.get(ID).toString()));
        sqlEntity.setJdbcId(Long.parseLong(map.get(JDBC_ID).toString()));
        sqlEntity.setSql(map.get(SQL).toString());
        return sqlEntity;
    }

    /**
     * 实体类转换为list
     *
     * @param sqlEntity
     * @return
     */
    private static List<Object> sqlToList(SqlEntity sqlEntity) {
        if (null == sqlEntity) {
            return null;
        }
        List<Object> list = new ArrayList<>();
        list.add(sqlEntity.getJdbcId());
        list.add(sqlEntity.getSql());
        return list;
    }
}
