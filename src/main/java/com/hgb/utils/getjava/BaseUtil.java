package com.hgb.utils.getjava;

import com.hgb.utils.getjava.bean.data.ColumnsData;
import com.hgb.utils.getjava.bean.data.TableData;
import com.hgb.utils.getjava.bean.request.DbRequest;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @Title: BaseUtil
 * @ProjectName util
 * @Description: TODO
 * @Author Guobin.Hu
 * @Date 2019/3/14 15:18
 */
public class BaseUtil implements Serializable{

    /**
     * 获取数据库连接信息
     * @param request 数据库连接信息
     * @author Guobin.Hu
     * @date 2019/3/13 17:51
     */
    public static Connection getConnection(DbRequest request){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Properties properties = new Properties();
            properties.setProperty("user",request.getDbUserName());
            properties.setProperty("password",request.getDbPassword());
            //设置可以获取remarks信息
            properties.setProperty("remarks","true");
            //设置可以获取tables remarks信息
            properties.setProperty("useInformationSchema","true");
            Connection conn = DriverManager.getConnection(request.getDbUrl(),properties);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭数据库连接
     * @param
     * @return
     * @author Guobin.Hu
     * @date 2019/3/13 19:38
     */
    public static void closeDb(Connection conn){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取表名
     * @param dbUserName 数据库用户名
     * @param tableName 要转换的表名
     * @return
     * @author Guobin.Hu
     * @date 2019/3/13 19:17
     */
    public static List<TableData> getTables(Connection conn, String dbUserName, List<String> tableName){
        try {
            DatabaseMetaData metaData = conn.getMetaData();
            List<TableData> table = new ArrayList<>();
            ResultSet rs = metaData.getTables(conn.getCatalog(),dbUserName,"%",new String[]{"TABLE"});
            while (rs.next()){
                TableData tableData = new TableData();
                tableData.setTableName(rs.getString("TABLE_NAME"));
                tableData.setTableDesc(rs.getString("REMARKS"));
                if(CollectionUtils.isEmpty(tableName) || tableName.contains(tableData.getTableName())) {
                    table.add(tableData);
                }
            }
            return table;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取列信息
     * @param
     * @return
     * @author Guobin.Hu
     * @date 2019/3/13 19:43
     */
    public static List<ColumnsData> getColumns(DatabaseMetaData metaData, String table) throws SQLException {
        List<ColumnsData> list = new ArrayList<>();
        ResultSet rsKey = metaData.getPrimaryKeys(null,null,table);
        List<String> keyList = new ArrayList<>();
        while (rsKey.next()){
            keyList.add(rsKey.getString("COLUMN_NAME"));
        }
        ResultSet rs = metaData.getColumns(null,null,table,null);
        while (rs.next()){
            ColumnsData columnsData = new ColumnsData();
            columnsData.setColumnsName(rs.getString("COLUMN_NAME"));
            columnsData.setColumnsNameJava(getClassFieldName(rs.getString("COLUMN_NAME"),false));
            columnsData.setColumnsType(getJavaType(rs.getString("TYPE_NAME")));
            columnsData.setColumnsDesc(rs.getString("REMARKS"));
            columnsData.setNull("YES".equals(rs.getString("IS_NULLABLE")) ? true : false);
            columnsData.setColumnSize(rs.getInt("COLUMN_SIZE"));
            if(keyList.contains(rs.getString("COLUMN_NAME"))){
                columnsData.setKey(true);
            }else{
                columnsData.setKey(false);
            }
            list.add(columnsData);
        }
        return list;
    }

    /**
     * 数据库字段类型，转换java基本数据类型
     * @param
     * @return
     * @author Guobin.Hu
     * @date 2019/3/14 15:24
     */
    private static String getJavaType(String dbType){
        if("BIGINT".equals(dbType.toUpperCase())){
            return "Long";
        }else if("INT".equals(dbType.toUpperCase())){
            return "Integer";
        }else if("BIT".equals(dbType.toUpperCase())){
            return "Boolean";
        }else if("TINYINT".equals(dbType.toUpperCase())){
            return "Byte";
        }else if("SMALLINT".equals(dbType.toUpperCase())){
            return "Short";
        }else if("FOLAT".equals(dbType.toUpperCase())){
            return "Float";
        }else if("DECIMAL".equals(dbType.toUpperCase()) || "NUMERIC".equals(dbType.toUpperCase())
                || "REAL".equals(dbType.toUpperCase()) || "MONEY".equals(dbType.toUpperCase())
                || "SMALLMONEY".equals(dbType.toUpperCase())){
            return "Double";
        }else if("DATETIME".equals(dbType.toUpperCase()) || "DATE".equals(dbType.toUpperCase())
                || "TIMESTAMP".equals(dbType.toUpperCase()) || "TIME".equals(dbType.toUpperCase())){
            return "Date";
        }else if("IMAGE".equals(dbType.toUpperCase())){
            return "Blod";
        }else{
            return "String";
        }
    }

    /**
     * 字段名转成驼峰形式
     * @param fieldName 要转换的字符串
     * @param firstUpper 首字母是否转换
     * @return
     * @author Guobin.Hu
     * @date 2019/3/13 20:53
     */
    public static String getClassFieldName(String fieldName,Boolean firstUpper){
        fieldName = firstUpper ? firstStrUpper(fieldName) : fieldName;
        if(fieldName.indexOf("_") == -1){
            return fieldName;
        }
        String[] str = fieldName.split("_");
        StringBuffer sb = new StringBuffer();
        sb.append(str[0]);
        for(int i = 1;i<str.length;i++){
            sb.append(firstStrUpper(str[i]));
        }
        return sb.toString();
    }

    /**
     * 首字母转大写
     * @param
     * @return
     * @author Guobin.Hu
     * @date 2019/3/13 21:00
     */
    private static String firstStrUpper(String str){
        char[] ch = str.toCharArray();
        if(ch[0] >= 'a' && ch[0] <= 'z'){
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }
}
