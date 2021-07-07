package com.maruko.service.impl;

import com.maruko.entity.BaseJdbcEntity;
import com.maruko.entity.ParamsEntity;
import com.maruko.enums.RespEnum;
import com.maruko.service.ParamsService;
import com.maruko.utils.JdbcUtil;
import com.maruko.vo.ResponseVo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
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
@Service
public class ParamsServiceImpl implements ParamsService {
    public static final String INSERT_SQL = "INSERT INTO `params_info`(`sql_id`, `name`, `value`, `type`) VALUES ( ?, ?, ?, ?)";

    public static final String ID = "id";

    public static final String SQL_ID = "sql_id";

    public static final String NAME = "name";

    public static final String VALUE = "value";

    public static final String TYPE = "type";

    public static final String QUERY_BY_SQLID_SQL = "select * from params_info where sql_id=?";

    /**
     * 添加参数信息
     *
     * @param baseJdbcEntity 初始化数据源
     * @param paramsEntity   参数实体
     * @return
     */
    @Override
    public ResponseVo addParams(BaseJdbcEntity baseJdbcEntity, ParamsEntity paramsEntity) {
        baseJdbcEntity = JdbcUtil.initBaseJdbc(baseJdbcEntity);
        JdbcUtil.executeInsert(baseJdbcEntity, INSERT_SQL, paramsToList(paramsEntity));
        return new ResponseVo(RespEnum.SUCCESS);
    }

    /**
     * 根据SQL id查询参数详情
     *
     * @param baseJdbcEntity
     * @param sqlId
     * @return
     */
    @Override
    public List<Object> queryParamsInfo(BaseJdbcEntity baseJdbcEntity, Long sqlId) {
        baseJdbcEntity = JdbcUtil.initBaseJdbc(baseJdbcEntity);
        List<Map<String, Object>> mapList = JdbcUtil.executeQuery(baseJdbcEntity, QUERY_BY_SQLID_SQL, Arrays.asList(sqlId));
        if (CollectionUtils.isEmpty(mapList)) {
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
     * @param map
     * @return
     */
    private ParamsEntity mapToParamsEntity(Map<String, Object> map) {
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
     * @param paramsEntity
     * @return
     */
    private List<Object> paramsToList(ParamsEntity paramsEntity) {
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
