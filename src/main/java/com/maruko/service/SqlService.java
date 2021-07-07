package com.maruko.service;

import com.maruko.entity.BaseJdbcEntity;
import com.maruko.entity.SqlEntity;
import com.maruko.vo.ResponseVo;

/**
 * 〈一句话功能简述〉<br>
 * 〈sql信息录入〉
 *
 * @author maruko
 * @date 2021/7/7 10:44
 * @since 1.0.0
 */

public interface SqlService {

    /**
     * 添加sql信息
     *
     * @param baseJdbcEntity 初始化数据源
     * @param sqlEntity      参数实体
     * @return
     */
    ResponseVo addSql(BaseJdbcEntity baseJdbcEntity, SqlEntity sqlEntity);

    /**
     * 执行SQL
     *
     * @param baseJdbcEntity 初始化数据源
     * @param id             sql编号
     * @return
     */
    ResponseVo executeSql(BaseJdbcEntity baseJdbcEntity, Long id);
}
