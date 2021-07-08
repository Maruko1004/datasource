package xyc.maruko.utils;


import xyc.maruko.config.JdbcConfig;
import xyc.maruko.entity.BaseJdbcEntity;
import xyc.maruko.expection.BusinessException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈数据库增删查改〉
 *
 * @author maruko
 * @date 2021/7/6 16:50
 * @since 1.0.0
 */
public class BaseJdbcUtil {

    /**
     * 初始化数据库配置
     *
     * @param baseJdbcEntity 数据库信息
     * @return BaseJdbcEntity
     */
    protected static BaseJdbcEntity initBaseJdbc(BaseJdbcEntity baseJdbcEntity) {
        if (null == baseJdbcEntity) {
            return new BaseJdbcEntity("MySQL", "jdbc:mysql://localhost:3307/maruko?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&allowMultiQueries=true&useSSL=false",
                    "root", "123456");
        }
        return baseJdbcEntity;
    }


    /**
     * 执行SQL
     *
     * @param connection 连接
     * @param sql        SQL语句
     * @param params     参数
     * @throws SQLException 异常
     */
    private static PreparedStatement prepareStatement(Connection connection, String sql, List<Object> params) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        if (null != params && params.size() > 0) {
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(i + 1, params.get(i));
            }
        }
        return preparedStatement;
    }

    /**
     * 条件查询
     *
     * @param jdbcEntity 实体
     * @param sql        SQL语句
     * @param params     参数
     * @return List<Map<String, Object>>
     */
    protected static List<Map<String, Object>> executeQuery(BaseJdbcEntity jdbcEntity, String sql,
                                                         List<Object> params) {
        List<Map<String, Object>> list = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcConfig.getConnection(jdbcEntity);
            preparedStatement = prepareStatement(connection, sql, params);
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int colsLen = metaData.getColumnCount();
            while (resultSet.next()) {
                Map<String, Object> map = new HashMap<>(5);
                for (int i = 0; i < colsLen; i++) {
                    String colsName = metaData.getColumnName(i + 1);
                    Object colsValue = resultSet.getObject(colsName);
                    if (colsValue == null) {
                        colsValue = "";
                    }
                    map.put(colsName, colsValue);
                }
                list.add(map);
            }
            return list;
        } catch (SQLException e) {
            throw new BusinessException("sql执行异常");
        } finally {
            close(resultSet, preparedStatement, connection);
        }
    }

    /**
     * 条件插入
     *
     * @param jdbcEntity 数据库实体
     * @param sql        sql语句
     * @param params     参数
     * @return int
     */
    protected static int executeInsert(BaseJdbcEntity jdbcEntity, String sql,
                                    List<Object> params) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = JdbcConfig.getConnection(jdbcEntity);
            preparedStatement = prepareStatement(connection, sql, params);
            //设置自动提交为true
            connection.setAutoCommit(true);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new BusinessException("sql执行异常");
        } finally {
            close(null, preparedStatement, connection);
        }
    }

    /**
     * 释放资源
     *
     * @param rs   结果集
     * @param st   参数集
     * @param conn 连接
     */
    private static void close(ResultSet rs, Statement st, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new BusinessException("数据库关闭异常");
            }
        }
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new BusinessException("数据库关闭异常");
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new BusinessException("数据库关闭异常");
            }
        }
    }

    public static void main(String[] args) {

    }
}
