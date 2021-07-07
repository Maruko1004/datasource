package com.maruko.service.impl;

import com.maruko.entity.BaseJdbcEntity;
import com.maruko.entity.JdbcEntity;
import com.maruko.entity.SqlEntity;
import com.maruko.enums.RespEnum;
import com.maruko.service.JdbcService;
import com.maruko.service.ParamsService;
import com.maruko.service.SqlService;
import com.maruko.utils.JdbcUtil;
import com.maruko.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
@Service
public class SqlServiceImpl implements SqlService {

    public static final String INSERT_SQL = "INSERT INTO `sql_info`( `jdbc_id`, `sql`) VALUES (?, ?)";

    public static final String EXECUTE_SQL = "select * from sql_info where id=?";

    public static final String ID = "id";

    public static final String JDBC_ID = "jdbc_id";

    public static final String SQL = "sql";

    @Autowired
    private JdbcService jdbcService;

    @Autowired
    private ParamsService paramsService;

    /**
     * 添加sql信息
     *
     * @param baseJdbcEntity 初始化数据源
     * @param sqlEntity      SQL实体
     * @return
     */
    @Override
    public ResponseVo addSql(BaseJdbcEntity baseJdbcEntity, SqlEntity sqlEntity) {
        baseJdbcEntity = JdbcUtil.initBaseJdbc(baseJdbcEntity);
        JdbcUtil.executeInsert(baseJdbcEntity, INSERT_SQL, sqlToList(sqlEntity));
        return new ResponseVo(RespEnum.SUCCESS);
    }

    /**
     * 执行SQL
     *
     * @param baseJdbcEntity 初始化数据源
     * @param id             编号
     * @return
     */
    @Override
    public ResponseVo executeSql(BaseJdbcEntity baseJdbcEntity, Long id) {
        baseJdbcEntity = JdbcUtil.initBaseJdbc(baseJdbcEntity);
        List<Map<String, Object>> list = JdbcUtil.executeQuery(baseJdbcEntity, EXECUTE_SQL, Arrays.asList(id));

        //查询出来的数据只会有一条
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, Object> map = list.get(0);
            SqlEntity sqlEntity = mapToSqlEntity(map);
            //获取数据库id
            Long jdbcId = sqlEntity.getJdbcId();
            //根据jdbcId查询jdbc连接信息
            JdbcEntity jdbcEntity = jdbcService.queryJdbcInfo(baseJdbcEntity, jdbcId);
            //根据id查询参数信息
            List<Object> paramsEntities = paramsService.queryParamsInfo(baseJdbcEntity, id);
            List<Map<String, Object>> mapList = JdbcUtil.executeQuery(jdbcEntity, sqlEntity.getSql(), paramsEntities);
            return new ResponseVo(RespEnum.SUCCESS, mapList);
        }

        return new ResponseVo(RespEnum.SUCCESS);
    }

    /**
     * map转换为实体
     *
     * @param map
     * @return
     */
    private SqlEntity mapToSqlEntity(Map<String, Object> map) {
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
    private List<Object> sqlToList(SqlEntity sqlEntity) {
        if (null == sqlEntity) {
            return null;
        }
        List<Object> list = new ArrayList<>();
        list.add(sqlEntity.getJdbcId());
        list.add(sqlEntity.getSql());
        return list;
    }
}
