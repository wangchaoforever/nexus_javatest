package com.hgb.utils.getjava.bean.data;

import java.io.Serializable;

/**
 * @Title: TableData
 * @ProjectName util
 * @Description: TODO
 * @Author Guobin.Hu
 * @Date 2019/3/13 19:30
 */
public class ColumnsData implements Serializable{
    /**
     * 数据库字段名
     */
    private String columnsName;
    /**
     * java驼峰字段名
     */
    private String columnsNameJava;
    /**
     * 字段描述
     */
    private String columnsDesc;
    /**
     * 字段类型(转java类型)
     */
    private String columnsType;
    /**
     * 是否允许为空
     */
    private Boolean isNull;
    /**
     * 最大长度
     */
    private Integer columnSize;
    /**
     * 是否主键
     */
    private Boolean isKey;

    public String getColumnsName() {
        return columnsName;
    }

    public void setColumnsName(String columnsName) {
        this.columnsName = columnsName;
    }

    public String getColumnsNameJava() {
        return columnsNameJava;
    }

    public void setColumnsNameJava(String columnsNameJava) {
        this.columnsNameJava = columnsNameJava;
    }

    public String getColumnsDesc() {
        return columnsDesc;
    }

    public void setColumnsDesc(String columnsDesc) {
        this.columnsDesc = columnsDesc;
    }

    public String getColumnsType() {
        return columnsType;
    }

    public void setColumnsType(String columnsType) {
        this.columnsType = columnsType;
    }

    public Boolean getNull() {
        return isNull;
    }

    public void setNull(Boolean aNull) {
        isNull = aNull;
    }

    public Integer getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(Integer columnSize) {
        this.columnSize = columnSize;
    }

    public Boolean getKey() {
        return isKey;
    }

    public void setKey(Boolean key) {
        isKey = key;
    }
}
