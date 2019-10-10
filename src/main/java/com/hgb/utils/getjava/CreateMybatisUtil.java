package com.hgb.utils.getjava;

import com.hgb.utils.getjava.bean.data.ColumnsData;
import com.hgb.utils.getjava.bean.data.TableData;
import com.hgb.utils.getjava.bean.request.DbRequest;

import java.io.Serializable;
import java.util.List;

/**
 * @Title: CreateMybatisUtil
 * @ProjectName util
 * @Description: 生成mybatis文件
 * @Author Guobin.Hu
 * @Date 2019/3/14 20:02
 */
public class CreateMybatisUtil implements Serializable{


    /**
     * 生成mybatis文件
     * @param table 表信息
     * @param columnsDataList 列信息
     * @param request 入参
     * @param keyData 主键数据
     * @return 
     * @author Guobin.Hu
     * @date 2019/3/14 20:04
     */
    public static String getMybatisClass(TableData table, List<ColumnsData> columnsDataList, DbRequest request, ColumnsData keyData){
        String xmlHeader = getHeader(table.getTableName(),request.getDaoPackageName());
        String xmlBaseColumn = getBaseColumn(columnsDataList);
        String xmlSelectByPrimaryKey = getSelectByPrimaryKey(table.getTableName(),request.getPojoPackageName(),keyData);
        String xmlSelectSelective = getSelectSelective(table.getTableName(),columnsDataList,request.getPojoPackageName());
        String xmlInsert = getInsert(table.getTableName(),columnsDataList,request.getPojoPackageName(),keyData);
        String xmlBatchInsert = getBatchInsert(table.getTableName(),columnsDataList);
        String xmlUpdateByPrimaryKey = getUpdateByPrimaryKey(table.getTableName(),columnsDataList,request.getPojoPackageName(),keyData);
        String xmlDelete = getDelete(table.getTableName(),keyData);
        String xmlBatchDelete = getBatchDelete(table.getTableName(),keyData);

        StringBuffer xml = new StringBuffer();
        xml.append(xmlHeader);
        xml.append("\r\r");
        xml.append(xmlBaseColumn);
        xml.append("\r\r");
        xml.append(xmlSelectByPrimaryKey);
        xml.append("\r\r");
        xml.append(xmlSelectSelective);
        xml.append("\r\r");
        xml.append(xmlInsert);
        xml.append("\r\r");
        xml.append(xmlBatchInsert);
        xml.append("\r\r");
        xml.append(xmlUpdateByPrimaryKey);
        xml.append("\r\r");
        xml.append(xmlDelete);
        xml.append("\r\r");
        xml.append(xmlBatchDelete);
        xml.append("\r\r");

        xml.append("</mapper>");
        return xml.toString();
    }

    /**
     * 主键删除(批量)
     * @param
     * @return
     * @author Guobin.Hu
     * @date 2019/4/26 15:41
     */
    private static String getBatchDelete(String tableName,ColumnsData keyName){
        StringBuffer batchDelete = new StringBuffer();
        batchDelete.append("\t<delete id=\"batchDelete\" parameterType=\"java.util.List\">");
        batchDelete.append("\r\t\tDELETE FROM ");
        batchDelete.append(tableName);
        batchDelete.append("\r\t\tWHERE ");
        batchDelete.append(keyName.getColumnsName());
        batchDelete.append(" IN ");
        batchDelete.append("\r\t\t<foreach collection=\"list\" item=\"item\" separator=\",\" open=\"(\" close=\")\">");
        batchDelete.append("\r\t\t\t#{item}\r\t\t</foreach>\r\t</delete>");
        return batchDelete.toString();
    }

    /**
     * 主键删除
     * @param
     * @return
     * @author Guobin.Hu
     * @date 2019/4/26 15:37
     */
    private static String getDelete(String tableName,ColumnsData keyName){
        StringBuffer delete = new StringBuffer();
        delete.append("\t<delete id=\"delete\" parameterType=\"java.lang.");
        delete.append(keyName.getColumnsType());
        delete.append("\">");
        delete.append("\r\t\tDELETE FROM ");
        delete.append(tableName);
        delete.append("\r\t\tWHERE ");
        delete.append(keyName.getColumnsName());
        delete.append(" = #{");
        delete.append(keyName.getColumnsNameJava());
        delete.append("}\r\t</delete>");
        return delete.toString();
    }

    /**
     * 修改
     * @param
     * @return
     * @author Guobin.Hu
     * @date 2019/4/26 15:32
     */
    private static String getUpdateByPrimaryKey(String tableName,List<ColumnsData> columnsDataList,String packageName,ColumnsData keyName){
        StringBuffer updateByPrimaryKey = new StringBuffer();
        updateByPrimaryKey.append("\t<update id=\"updateByPrimaryKey\" parameterType=\"");
        updateByPrimaryKey.append(packageName);
        updateByPrimaryKey.append(".");
        updateByPrimaryKey.append(BaseUtil.getClassFieldName(tableName,true));
        updateByPrimaryKey.append("\">\r");
        updateByPrimaryKey.append("\t\tUPDATE ");
        updateByPrimaryKey.append(tableName);
        updateByPrimaryKey.append(getUpdateXml(columnsDataList));
        updateByPrimaryKey.append("\r\t\tWHERE ");
        updateByPrimaryKey.append(keyName.getColumnsName());
        updateByPrimaryKey.append(" = #{");
        updateByPrimaryKey.append(keyName.getColumnsNameJava());
        updateByPrimaryKey.append("}\r\t</update>");
        return updateByPrimaryKey.toString();
    }

    /**
     * 获取赋值xml
     * @param
     * @return
     * @author Guobin.Hu
     * @date 2019/4/26 15:25
     */
    private static String getUpdateXml(List<ColumnsData> columnsDataList){
        StringBuffer updateXml = new StringBuffer();
        updateXml.append("\r\t\t<set>");
        for (ColumnsData columnsData: columnsDataList) {
            updateXml.append("\r\t\t\t<if test=\"");
            updateXml.append(columnsData.getColumnsNameJava());
            updateXml.append(" != null ");
            if(!"Date".equals(columnsData.getColumnsType())) {
                updateXml.append("and ");
                updateXml.append(columnsData.getColumnsNameJava());
                updateXml.append(" != ''");
            }
            updateXml.append("\">\r\t\t\t\t");
            updateXml.append(columnsData.getColumnsName());
            updateXml.append(" = #{");
            updateXml.append(columnsData.getColumnsNameJava());
            updateXml.append("},");
            updateXml.append("\r\t\t\t</if>");
        }
        updateXml.append("\r\t\t</set>");
        return updateXml.toString().replace(",\r\t\t\t</if>\r\t\t</set>","\r\t\t\t</if>\r\t\t</set>");
    }

    /**
     * 批量新增
     * @param
     * @return
     * @author Guobin.Hu
     * @date 2019/4/26 15:00
     */
    private static String getBatchInsert(String tableName,List<ColumnsData> columnsDataList){
        StringBuffer batchInsert = new StringBuffer();
        batchInsert.append("\t<insert id=\"batchInsert\" parameterType=\"java.util.List\">\r");
        batchInsert.append("\t\tINSERT INTO ");
        batchInsert.append(tableName);
        batchInsert.append(" (");
        batchInsert.append(getColumnsDataXml(columnsDataList));
        batchInsert.append("\r\t\t)\r\t\tVALUES ");
        batchInsert.append(getBatchColumnsDataJavaXml(columnsDataList));
        batchInsert.append("\r\t</insert>");
        return batchInsert.toString();
    }

    /**
     * 获取数据库字段转JAVA列表String(批量)
     * @param
     * @return
     * @author Guobin.Hu
     * @date 2019/4/26 14:52
     */
    private static String getBatchColumnsDataJavaXml(List<ColumnsData> columnsDataList){
        StringBuffer batchInsertXml = new StringBuffer();
        batchInsertXml.append("\r\t\t<foreach collection=\"list\" item=\"item\" separator=\",\">");
        batchInsertXml.append("\r\t\t(");
        for (ColumnsData columnsData : columnsDataList){
            batchInsertXml.append("\r\t\t\t#{item.");
            batchInsertXml.append(columnsData.getColumnsNameJava());
            batchInsertXml.append("},");
        }
        batchInsertXml.append("\r\t\t)");
        batchInsertXml.append("\r\t\t</foreach>");
        return batchInsertXml.toString().replace(",\r\t\t)","\r\t\t)");
    }

    /**
     * 新增
     * @param
     * @return
     * @author Guobin.Hu
     * @date 2019/4/26 14:43
     */
    private static String getInsert(String tableName,List<ColumnsData> columnsDataList,String packageName,ColumnsData keyName){
        StringBuffer insert = new StringBuffer();
        insert.append("\t<insert id=\"insert\" parameterType=\"");
        insert.append(packageName);
        insert.append(".");
        insert.append(BaseUtil.getClassFieldName(tableName,true));
        insert.append("\" keyProperty=\"");
        insert.append(keyName.getColumnsNameJava());
        insert.append("\" useGeneratedKeys=\"true\">\r");
        insert.append("\t\tINSERT INTO ");
        insert.append(tableName);
        insert.append(" (");
        insert.append(getColumnsDataXml(columnsDataList));
        insert.append("\r\t\t)\r\t\tVALUES ");
        insert.append(getColumnsDataJavaXml(columnsDataList));
        insert.append("\r\t</insert>");
        return insert.toString();
    }

    /**
     * 获取数据库字段转JAVA列表String(单个)
     * @param
     * @return
     * @author Guobin.Hu
     * @date 2019/4/26 14:52
     */
    private static String getColumnsDataJavaXml(List<ColumnsData> columnsDataList){
        StringBuffer insertXml = new StringBuffer();
        insertXml.append("(");
        for (ColumnsData columnsData : columnsDataList){
            insertXml.append("\r\t\t\t#{");
            insertXml.append(columnsData.getColumnsNameJava());
            insertXml.append("},");
        }
        insertXml.append("\r\t\t)");
        return insertXml.toString().replace(",\r\t\t)","\r\t\t)");
    }

    /**
     * 获取数据库字段列表String
     * @param
     * @return
     * @author Guobin.Hu
     * @date 2019/4/26 14:52
     */
    private static String getColumnsDataXml(List<ColumnsData> columnsDataList){
        StringBuffer insertXml = new StringBuffer();
        for (ColumnsData columnsData : columnsDataList){
            insertXml.append("\r\t\t\t");
            insertXml.append(columnsData.getColumnsName());
            insertXml.append(",");
        }
        return insertXml.toString().substring(0,insertXml.length()-1);
    }

    /**
     * 根据对象属性查询对象列表
     * @param
     * @return
     * @author Guobin.Hu
     * @date 2019/3/15 11:24
     */
    private static String getSelectSelective(String tableName,List<ColumnsData> columnsDataList,String packageName){
        StringBuffer selectSelective = new StringBuffer();
        selectSelective.append("\t<select id=\"selectSelective\" parameterType=\"");
        selectSelective.append(packageName);
        selectSelective.append(".");
        selectSelective.append(BaseUtil.getClassFieldName(tableName,true));
        selectSelective.append("\" resultType=\"");
        selectSelective.append(packageName);
        selectSelective.append(".");
        selectSelective.append(BaseUtil.getClassFieldName(tableName,true));
        selectSelective.append("\">\r");
        selectSelective.append("\t\tSELECT \r\t\t<include refid=\"Base_Column_List\"/>\r\t\tFROM ");
        selectSelective.append(tableName);
        selectSelective.append(getWhereXml(columnsDataList));
        selectSelective.append("\r\t</select>");
        return selectSelective.toString();
    }

    /**
     * 获取where条件的数据
     * @param
     * @return
     * @author Guobin.Hu
     * @date 2019/4/26 14:06
     */
    private static String getWhereXml(List<ColumnsData> columnsDataList){
        StringBuffer whereSelective = new StringBuffer();
        whereSelective.append("\r\t\t<where>");
        for (ColumnsData columnsData: columnsDataList) {
            whereSelective.append("\r\t\t\t<if test=\"");
            whereSelective.append(columnsData.getColumnsNameJava());
            whereSelective.append(" != null ");
            if(!"Date".equals(columnsData.getColumnsType())) {
                whereSelective.append("and ");
                whereSelective.append(columnsData.getColumnsNameJava());
                whereSelective.append(" != ''");
            }
            whereSelective.append("\">\r\t\t\t\tAND ");
            whereSelective.append(columnsData.getColumnsName());
            whereSelective.append(getLikeOrEquals(columnsData.getColumnsType(),columnsData.getColumnsNameJava()));
            whereSelective.append("\r\t\t\t</if>");
        }
        whereSelective.append("\r\t\t</where>");
        return whereSelective.toString();
    }

    /**
     * 获取类型转换
     * @param 
     * @return 
     * @author Guobin.Hu
     * @date 2019/4/26 14:34
     */
    private static String getLikeOrEquals(String columnsType,String columnsNameJava){
        if ("String".equals(columnsType)){
            return " LIKE CONCAT('%',#{" + columnsNameJava + "},'%') ";
        }else {
            return " = #{" + columnsNameJava + "} ";
        }
    }

    /**
     * 根据主键查询对象
     * @param tableName 表名
     * @param packageName 实体包名
     * @param keyName id列数据
     * @return
     * @author Guobin.Hu
     * @date 2019/3/15 9:49
     */
    private static String getSelectByPrimaryKey(String tableName,String packageName,ColumnsData keyName){
        StringBuffer selectByPrimaryKey = new StringBuffer();
        selectByPrimaryKey.append("\t<select id=\"selectByPrimaryKey\" parameterType=\"java.lang.");
        selectByPrimaryKey.append(keyName.getColumnsType());
        selectByPrimaryKey.append("\" resultType=\"");
        selectByPrimaryKey.append(packageName);
        selectByPrimaryKey.append(".");
        selectByPrimaryKey.append(BaseUtil.getClassFieldName(tableName,true));
        selectByPrimaryKey.append("\">\r");
        selectByPrimaryKey.append("\t\tSELECT \r\t\t<include refid=\"Base_Column_List\"/>\r\t\tFROM ");
        selectByPrimaryKey.append(tableName);
        selectByPrimaryKey.append("\r\t\tWHERE ");
        selectByPrimaryKey.append(keyName.getColumnsName());
        selectByPrimaryKey.append(" = #{");
        selectByPrimaryKey.append(keyName.getColumnsNameJava());
        selectByPrimaryKey.append("}\r\t</select>");
        return selectByPrimaryKey.toString();
    }

    /**
     * 获取输出字段
     * @param columnsDataList 字段信息
     * @return 
     * @author Guobin.Hu
     * @date 2019/3/14 20:16
     */
    private static String getBaseColumn(List<ColumnsData> columnsDataList){
        StringBuffer baseColumn = new StringBuffer();
        baseColumn.append("\t<!-- 输出字段列 -->\r\t<sql id=\"Base_Column_List\">\r");
        for (ColumnsData columnsData: columnsDataList) {
            baseColumn.append("\t\t");
            baseColumn.append(columnsData.getColumnsName());
            baseColumn.append(" AS \"");
            baseColumn.append(columnsData.getColumnsNameJava());
            baseColumn.append("\",\r");
        }
        baseColumn.append("\t</sql>");
        return baseColumn.toString().replace(",\r\t</sql>","\r\t</sql>");
    }

    /**
     * 获取mapper头
     * @param
     * @return
     * @author Guobin.Hu
     * @date 2019/3/14 20:12
     */
    private static String getHeader(String table,String nameSpace){
        StringBuffer header = new StringBuffer();
        header.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n");
        header.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n");
        header.append("<mapper namespace=\"");
        header.append(nameSpace);
        header.append(".");
        header.append(BaseUtil.getClassFieldName(table,true));
        header.append("Dao\">");
        return header.toString();
    }
}
