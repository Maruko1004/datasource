package com.maruko.service;

import com.maruko.entity.BaseJdbcEntity;
import com.maruko.entity.ParamsEntity;
import com.maruko.vo.ResponseVo;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈参数信息录入〉
 *
 * @author maruko
 * @date 2021/7/7 10:45
 * @since 1.0.0
 */

public interface ParamsService {

    /**
     * 添加参数信息
     *
     * @param baseJdbcEntity 初始化数据源
     * @param paramsEntity   参数实体
     * @return
     */
    ResponseVo addParams(BaseJdbcEntity baseJdbcEntity, ParamsEntity paramsEntity);

    /**
     * 根据SQL id查询参数详情
     *
     * @param baseJdbcEntity
     * @param sqlId
     * @return
     */
    List<Object> queryParamsInfo(BaseJdbcEntity baseJdbcEntity, Long sqlId);
}
