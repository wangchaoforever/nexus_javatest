package com.hgb.utils.getjava.bean.data;

import java.io.Serializable;

/**
 * @Title: TableData
 * @ProjectName util
 * @Description: 获取表信息
 * @Author Guobin.Hu
 * @Date 2019/3/14 17:53
 */
public class TableData implements Serializable{

    /**
     * 表名
     */
    private String tableName;
    /**
     * 表描述
     */
    private String tableDesc;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableDesc() {
        return tableDesc;
    }

    public void setTableDesc(String tableDesc) {
        this.tableDesc = tableDesc;
    }
}
