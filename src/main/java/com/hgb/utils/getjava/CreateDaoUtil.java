package com.hgb.utils.getjava;

import com.hgb.utils.getjava.bean.data.TableData;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Title: CreateDaoUtil
 * @ProjectName util
 * @Description: 创建dao类
 * @Author Guobin.Hu
 * @Date 2019/3/14 18:56
 */
public class CreateDaoUtil implements Serializable{

    /**
     * 获取dao文件
     * @param 
     * @return 
     * @author Guobin.Hu
     * @date 2019/3/14 19:34
     */
    public static String getDaoClass(String packageName,TableData table, String pojoPackageName, String keyType){
        StringBuffer className = new StringBuffer();
        className.append("package ");
        className.append(packageName);
        className.append(";\r\r");
        className.append("import ");
        className.append(packageName);
        className.append(".BaseDao;\r");
        className.append("import ");
        className.append(pojoPackageName);
        className.append(".");
        className.append(BaseUtil.getClassFieldName(table.getTableName(),true));
        className.append(";\r");
        className.append("import org.springframework.stereotype.Repository;\r\r");
        className.append("/**\r * ");
        className.append(table.getTableDesc());
        className.append("Dao\r");
        className.append(" * @author 自动生成\r * @date ");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        className.append(sdf.format(new Date()));
        className.append("\r **/\r");
        className.append("@Repository\rpublic interface ");
        className.append(BaseUtil.getClassFieldName(table.getTableName(),true));
        className.append("Dao extends BaseDao<");
        className.append(BaseUtil.getClassFieldName(table.getTableName(),true));
        className.append(",");
        className.append(keyType);
        className.append("> {\r\r\r}");
        return className.toString();
    }

    /**
     * 生成baseDao
     * @param
     * @return
     * @author Guobin.Hu
     * @date 2019/3/14 19:15
     */
    public static String getBaseDaoClass(String packageName){
        StringBuffer className = new StringBuffer();
        className.append("package ");
        className.append(packageName);
        className.append(";\r\r");
        className.append("import java.util.List;\r\r");
        className.append("/**\r * dao基类\r * @author 自动生成\r * @date ");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        className.append(sdf.format(new Date()));
        className.append("\r **/\rpublic interface BaseDao<T,N> {\r");

        className.append("    /**\r     * 根据主键查询对象\r     * @param id 主键\r     * @author 自动生成\r     * @date ");
        className.append(sdf.format(new Date()));
        className.append("\r     */\r");
        className.append("    T selectByPrimaryKey(N id);\r\r");

        className.append("    /**\r     * 根据对象属性查询对象列表（不分页）\r     * @param entity 对象属性\r     * @author 自动生成\r     * @date ");
        className.append(sdf.format(new Date()));
        className.append("\r     */\r");
        className.append("    List<T> selectSelective(T entity);\r\r");

        className.append("    /**\r     * 新增对象\r     * @param entity 对象\r     * @author 自动生成\r     * @date ");
        className.append(sdf.format(new Date()));
        className.append("\r     */\r");
        className.append("    int insert(T entity);\r\r");

        className.append("    /**\r     * 批量新增对象\r     * @param entity 对象\r     * @author 自动生成\r     * @date ");
        className.append(sdf.format(new Date()));
        className.append("\r     */\r");
        className.append("    int batchInsert(List<T> entity);\r\r");

        className.append("    /**\r     * 修改对象\r     * @param entity 对象\r     * @author 自动生成\r     * @date ");
        className.append(sdf.format(new Date()));
        className.append("\r     */\r");
        className.append("    int updateByPrimaryKey(T entity);\r\r");

        className.append("    /**\r     * 删除对象\r     * @param id 主键\r     * @author 自动生成\r     * @date ");
        className.append(sdf.format(new Date()));
        className.append("\r     */\r");
        className.append("    int delete(N id);\r\r");

        className.append("    /**\r     * 批量删除对象\r     * @param ids 主键列表\r     * @author 自动生成\r     * @date ");
        className.append(sdf.format(new Date()));
        className.append("\r     */\r");
        className.append("    int batchDelete(List<N> ids);\r\r");

        className.append("}");
        return className.toString();
    }

}
