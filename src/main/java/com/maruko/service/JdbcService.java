package com.maruko.service;

import com.maruko.entity.BaseJdbcEntity;
import com.maruko.entity.JdbcEntity;
import com.maruko.vo.ResponseVo;

/**
 * 〈一句话功能简述〉<br>
 * 〈数据库信息录入〉
 *
 * @author maruko
 * @date 2021/7/7 10:42
 * @since 1.0.0
 */

public interface JdbcService {

    /**
     * 添加jdbc连接信息
     *
     * @param baseJdbcEntity 初始化数据源
     * @param jdbcEntity     数据库信息
     * @return
     */
    ResponseVo addJdbc(BaseJdbcEntity baseJdbcEntity, JdbcEntity jdbcEntity);

    /**
     * 根据id查询数据库信息
     * @param baseJdbcEntity
     * @param id
     * @return
     */
    JdbcEntity queryJdbcInfo(BaseJdbcEntity baseJdbcEntity, Long id);
}
