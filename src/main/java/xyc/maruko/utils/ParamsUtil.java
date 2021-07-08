package xyc.maruko.utils;

import xyc.maruko.entity.BaseJdbcEntity;
import xyc.maruko.entity.ParamsEntity;
import xyc.maruko.enums.RespEnum;
import xyc.maruko.vo.ResponseVo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈参数信息录入实现层〉
 *
 * @author maruko
 * @date 2021/7/7 10:46
 * @since 1.0.0
 */

public class ParamsUtil {
    private static final String INSERT_SQL = "INSERT INTO `params_info`(`sql_id`, `name`, `value`, `type`) VALUES ( ?, ?, ?, ?)";

    private static final String ID = "id";

    private static final String SQL_ID = "sql_id";

    private static final String NAME = "name";

    private static final String VALUE = "value";

    private static final String TYPE = "type";

    private static final String QUERY_BY_SQL_ID_SQL = "select * from params_info where sql_id=?";

    /**
     * 添加参数信息
     *
     * @param baseJdbcEntity 初始化数据源
     * @param paramsEntity   参数实体
     * @return ResponseVo
     */
    public static ResponseVo addParams(BaseJdbcEntity baseJdbcEntity, ParamsEntity paramsEntity) {
        baseJdbcEntity = BaseJdbcUtil.initBaseJdbc(baseJdbcEntity);
        BaseJdbcUtil.executeInsert(baseJdbcEntity, INSERT_SQL, paramsToList(paramsEntity));
        return new ResponseVo(RespEnum.SUCCESS);
    }

    /**
     * 根据SQL id查询参数详情
     *
     * @param baseJdbcEntity 初始化数据源
     * @param sqlId          sql编号
     * @return List<Object>
     */
    protected static List<Object> queryParamsInfo(BaseJdbcEntity baseJdbcEntity, Long sqlId) {
        baseJdbcEntity = BaseJdbcUtil.initBaseJdbc(baseJdbcEntity);
        List<Map<String, Object>> mapList = BaseJdbcUtil.executeQuery(baseJdbcEntity, QUERY_BY_SQL_ID_SQL, Collections.singletonList(sqlId));
        if (null == mapList || mapList.size() == 0) {
            return null;
        }
        List<Object> list = new ArrayList<>();
        for (Map<String, Object> map : mapList) {
            list.add(map.get(VALUE).toString());
        }
        return list;
    }

    /**
     * map转换为实体
     *
     * @param map map
     * @return ParamsEntity
     */
    private static ParamsEntity mapToParamsEntity(Map<String, Object> map) {
        ParamsEntity paramsEntity = new ParamsEntity();
        paramsEntity.setId(Long.parseLong(map.get(ID).toString()));
        paramsEntity.setSqlId(Long.parseLong(map.get(SQL_ID).toString()));
        paramsEntity.setName(map.get(NAME).toString());
        paramsEntity.setValue(map.get(VALUE).toString());
        paramsEntity.setType(map.get(TYPE).toString());
        return paramsEntity;
    }

    /**
     * 实体类转换为list
     *
     * @param paramsEntity 实体
     * @return List<Object>
     */
    private static List<Object> paramsToList(ParamsEntity paramsEntity) {
        if (null == paramsEntity) {
            return null;
        }
        List<Object> list = new ArrayList<>();
        list.add(paramsEntity.getSqlId());
        list.add(paramsEntity.getName());
        list.add(paramsEntity.getValue());
        list.add(paramsEntity.getType());
        return list;
    }
}
