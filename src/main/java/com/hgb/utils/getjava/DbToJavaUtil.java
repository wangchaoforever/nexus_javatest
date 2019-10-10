package com.hgb.utils.getjava;

import com.hgb.utils.getjava.bean.data.ColumnsData;
import com.hgb.utils.getjava.bean.data.TableData;
import com.hgb.utils.getjava.bean.request.DbRequest;
import org.springframework.util.StringUtils;
import sun.security.x509.AttributeNameEnumeration;

import javax.validation.Valid;
import java.io.Serializable;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @Title: DbToJavaUtil
 * @ProjectName util
 * @Description: 数据库表转成java类
 * @Author Guobin.Hu
 * @Date 2019/3/13 17:25
 */
public class DbToJavaUtil implements Serializable{

    public static void main(String[] args) {
//        DbRequest request = new DbRequest();
//        request.setDbUrl("jdbc:mysql://localhost:3306/e_book?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true");
//        request.setDbUserName("root");
//        request.setDbPassword("root");
//        request.setPojoOutAdr("D:\\hgb\\e-Book\\system\\src\\main\\java\\com\\huplatform\\ebook\\entity");
//        request.setPojoPackageName("com.huplatform.ebook.entity");
//        request.setDaoOutAdr("D:\\hgb\\e-Book\\system\\src\\main\\java\\com\\huplatform\\ebook\\dao");
//        request.setDaoPackageName("com.huplatform.ebook.dao");
//        request.setXmlOutAdr("D:\\hgb\\e-Book\\system\\src\\main\\resources\\mybatis");
//        getJavaFile(request);

    }

    /**
     * 自动生成java文件
     * @param request 入参
     * @return
     * @author Guobin.Hu
     * @date 2019/3/14 19:06
     */
    public static void getJavaFile(DbRequest request){
        Connection conn = BaseUtil.getConnection(request);
        if(conn == null){
            throw new RuntimeException("数据库连接异常！");
        }
        try {
            List<TableData> tables = BaseUtil.getTables(conn,request.getDbUserName(),request.getTableName());
            getJava(conn, tables, request);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseUtil.closeDb(conn);
        }
    }

    /**
     * 通过表名获取java文件
     * @param 
     * @return 
     * @author Guobin.Hu
     * @date 2019/3/13 19:38
     */
    private static void getJava(Connection conn,List<TableData> tables,DbRequest request) throws SQLException {
        DatabaseMetaData metaData = conn.getMetaData();

        //获取baseDao --> java文件
        if(request.getShowBaseDao()) {
            String baseDaoClass = CreateDaoUtil.getBaseDaoClass(request.getDaoPackageName());
            FileUtil.outFile("BaseDao.java", baseDaoClass, request.getDaoOutAdr());
        }

        for (TableData table: tables) {
            List<ColumnsData> columnsDataList = BaseUtil.getColumns(metaData,table.getTableName().toUpperCase());
            List<ColumnsData> keyColumns = columnsDataList.stream().filter(o -> o.getKey()).collect(Collectors.toList());
            //获取entity --> java文件
            String pojoClass = CreatePojoUtil.getPojoClass(table,columnsDataList,request);
            FileUtil.outFile(BaseUtil.getClassFieldName(table.getTableName(),true)+".java",pojoClass,request.getPojoOutAdr());
            //获取dao --> java文件
            String daoClass = CreateDaoUtil.getDaoClass(request.getDaoPackageName(),table, request.getPojoPackageName(),keyColumns.get(0).getColumnsType());
            FileUtil.outFile(BaseUtil.getClassFieldName(table.getTableName(),true)+"Dao.java",daoClass,request.getDaoOutAdr());
            //获取mybatis --> xml文件
            String mybatisClass = CreateMybatisUtil.getMybatisClass(table,columnsDataList,request,keyColumns.get(0));
            FileUtil.outFile(BaseUtil.getClassFieldName(table.getTableName(),true)+"Mapper.xml",mybatisClass,request.getXmlOutAdr());

        }
    }

}
