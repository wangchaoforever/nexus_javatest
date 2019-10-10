package com.hgb.utils.getjava.bean.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @Title: dbRequest
 * @ProjectName util
 * @Description: 数据库连接信息入参
 * @Author Guobin.Hu
 * @Date 2019/3/13 17:32
 */
public class DbRequest implements Serializable{

    /**
     * 数据源地址
     */
    @NotBlank(message = "数据源地址不能为空")
    private String dbUrl;
    /**
     * 数据库用户名
     */
    @NotBlank(message = "数据库用户名不能为空")
    private String dbUserName;
    /**
     * 数据库密码
     */
    @NotBlank(message = "数据库密码不能为空")
    private String dbPassword;
    /**
     * dao包名
     */
    @NotBlank(message = "dao包名不能为空")
    private String daoPackageName;
    /**
     * dao文件生成路径
     */
    @NotBlank(message = "dao文件生成路径不能为空")
    private String daoOutAdr;
    /**
     * pojo包名
     */
    @NotBlank(message = "pojo包名不能为空")
    private String pojoPackageName;
    /**
     * pojo文件生成路径
     */
    @NotBlank(message = "pojo文件生成路径不能为空")
    private String pojoOutAdr;
    /**
     * xml文件生成路径
     */
    @NotBlank(message = "xml文件生成路径不能为空")
    private String xmlOutAdr;
    /**
     * 要转换的数据库表名称
     */
    private List<String> tableName;
    /**
     * 是否生成baseDao
     */
    @NotNull(message = "是否生成baseDao不能为空")
    private Boolean showBaseDao;

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbUserName() {
        return dbUserName;
    }

    public void setDbUserName(String dbUserName) {
        this.dbUserName = dbUserName;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getDaoPackageName() {
        return daoPackageName;
    }

    public void setDaoPackageName(String daoPackageName) {
        this.daoPackageName = daoPackageName;
    }

    public String getDaoOutAdr() {
        return daoOutAdr;
    }

    public void setDaoOutAdr(String daoOutAdr) {
        this.daoOutAdr = daoOutAdr;
    }

    public String getPojoPackageName() {
        return pojoPackageName;
    }

    public void setPojoPackageName(String pojoPackageName) {
        this.pojoPackageName = pojoPackageName;
    }

    public String getPojoOutAdr() {
        return pojoOutAdr;
    }

    public void setPojoOutAdr(String pojoOutAdr) {
        this.pojoOutAdr = pojoOutAdr;
    }

    public String getXmlOutAdr() {
        return xmlOutAdr;
    }

    public void setXmlOutAdr(String xmlOutAdr) {
        this.xmlOutAdr = xmlOutAdr;
    }

    public List<String> getTableName() {
        return tableName;
    }

    public void setTableName(List<String> tableName) {
        this.tableName = tableName;
    }

    public Boolean getShowBaseDao() {
        return showBaseDao;
    }

    public void setShowBaseDao(Boolean showBaseDao) {
        this.showBaseDao = showBaseDao;
    }
}
