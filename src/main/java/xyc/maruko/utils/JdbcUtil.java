package xyc.maruko.utils;


import xyc.maruko.config.JdbcConfig;
import xyc.maruko.entity.BaseJdbcEntity;

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
public class JdbcUtil {

    /**
     * 初始化数据库配置
     *
     * @param baseJdbcEntity
     * @return
     */
    public static BaseJdbcEntity initBaseJdbc(BaseJdbcEntity baseJdbcEntity) {
        if (null == baseJdbcEntity) {
            return new BaseJdbcEntity("MySQL", "jdbc:mysql://localhost:3307/maruko?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&allowMultiQueries=true&useSSL=false",
                    "root", "123456");
        }
        return baseJdbcEntity;
    }


    /**
     * 执行SQL
     *
     * @param connection
     * @param sql
     * @param params
     * @throws Exception
     */
    public static PreparedStatement prepareStatement(Connection connection, String sql, List<Object> params) throws SQLException {
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
     * @param jdbcEntity
     * @param sql
     * @param params
     * @return
     */
    public static List<Map<String, Object>> executeQuery(BaseJdbcEntity jdbcEntity, String sql,
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
                Map<String, Object> map = new HashMap<>();
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

        } finally {
            close(resultSet, preparedStatement, connection);
        }
        return null;
    }

    /**
     * 条件插入
     *
     * @param jdbcEntity
     * @param sql
     * @param params
     * @return
     */
    public static int executeInsert(BaseJdbcEntity jdbcEntity, String sql,
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
            e.printStackTrace();
            return 0;
        } finally {
            close(null, preparedStatement, connection);
        }
    }

    /**
     * 释放资源
     *
     * @param rs
     * @param st
     * @param conn
     */
    public static void close(ResultSet rs, Statement st, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {

            }
        }
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {

            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {

            }
        }
    }

    public static void main(String[] args) {

    }
}
