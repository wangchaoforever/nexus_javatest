package com.hgb.utils.getjava;

import com.hgb.utils.getjava.bean.data.ColumnsData;
import com.hgb.utils.getjava.bean.data.TableData;
import com.hgb.utils.getjava.bean.request.DbRequest;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Title: CreateClassUtil
 * @ProjectName util
 * @Description: 生成POJO类
 * @Author Guobin.Hu
 * @Date 2019/3/14 15:00
 */
public class CreatePojoUtil implements Serializable{

    /**
     * 获取pojo类
     * @param table 表名
     * @param columnsDataList 列信息名
     * @param request 入参
     * @return
     * @author Guobin.Hu
     * @date 2019/3/14 15:06
     */
    public static String getPojoClass(TableData table, List<ColumnsData> columnsDataList, DbRequest request){
        String classPackage = getPackage(request.getPojoPackageName());
        String classImport = getImport(columnsDataList);
        String className = getClassName(table);
        String classField = getField(columnsDataList);
        String classGetSet = getGetSet(columnsDataList);

        StringBuffer pojoClass = new StringBuffer();
        pojoClass.append(classPackage);
        pojoClass.append("\r");
        pojoClass.append(classImport);
        pojoClass.append("\r");
        pojoClass.append(className);
        pojoClass.append("{\r\r");
        pojoClass.append(classField);
        pojoClass.append("\r");
        pojoClass.append(classGetSet);
        pojoClass.append("}");
        return pojoClass.toString();
    }

    /**
     * 获取getter/setter
     * @param columnsDataList 列信息
     * @return getter/setter
     * @author Guobin.Hu
     * @date 2019/3/14 16:26
     */
    private static String getGetSet(List<ColumnsData> columnsDataList){
        StringBuffer getSet = new StringBuffer();
        for (ColumnsData columnsData: columnsDataList) {
            if("Date".equals(columnsData.getColumnsType())){
                getSet.append("    @JsonFormat(pattern = \"yyyy-MM-dd HH:mm:ss\")\r");
            }
            if(!columnsData.getNull() && !"String".equals(columnsData.getColumnsType())){
                getSet.append("    @NotNull(message = \"");
                getSet.append(columnsData.getColumnsDesc());
                getSet.append("不能为空\")\r");
            }
            if(!"Date".equals(columnsData.getColumnsType()) && columnsData.getColumnSize() != 0){
                getSet.append("    @Length(min = ");
                getSet.append(columnsData.getNull() ? 0 : 1);
                getSet.append(", max = ");
                getSet.append(columnsData.getColumnSize());
                getSet.append(", message = \"");
                getSet.append(columnsData.getColumnsDesc());
                getSet.append("长度必须介于 ");
                getSet.append(columnsData.getNull() ? 0 : 1);
                getSet.append(" 和 ");
                getSet.append(columnsData.getColumnSize());
                getSet.append(" 之间\")\r");
            }
            getSet.append("    public ");
            getSet.append(columnsData.getColumnsType());
            getSet.append(" get");
            getSet.append(BaseUtil.getClassFieldName(columnsData.getColumnsName(),true));
            getSet.append("() {\r");
            getSet.append("        return ");
            getSet.append(columnsData.getColumnsNameJava());
            getSet.append(";\r    }\r\r");
            getSet.append("    public void set");
            getSet.append(BaseUtil.getClassFieldName(columnsData.getColumnsName(),true));
            getSet.append("(");
            getSet.append(columnsData.getColumnsType());
            getSet.append(" ");
            getSet.append(columnsData.getColumnsNameJava());
            getSet.append(") {\r        this.");
            getSet.append(columnsData.getColumnsNameJava());
            getSet.append(" = ");
            getSet.append(columnsData.getColumnsNameJava());
            getSet.append(";\r    }\r\r");
        }
        return getSet.toString();
    }

    /**
     * 获取类属性
     * @param columnsDataList 列信息
     * @return 类属性
     * @author Guobin.Hu
     * @date 2019/3/14 16:18
     */
    private static String getField(List<ColumnsData> columnsDataList){
        StringBuffer field = new StringBuffer();
        for (ColumnsData columnsData: columnsDataList) {
            field.append("    /**\r");
            field.append("     * ");
            field.append(columnsData.getColumnsDesc());
            field.append("\r     **/\r    private ");
            field.append(columnsData.getColumnsType());
            field.append(" ");
            field.append(columnsData.getColumnsNameJava());
            field.append(";\r");
        }
        return field.toString();
    }

    /**
     * 获取类名信息
     * @param tableName 表名
     * @return
     * @author Guobin.Hu
     * @date 2019/3/14 16:10
     */
    private static String getClassName(TableData tableName){
        StringBuffer className = new StringBuffer();
        className.append("/**\r * ");
        className.append(tableName.getTableDesc());
        className.append("\r * @author 自动生成\r * @date ");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        className.append(sdf.format(new Date()));
        className.append("\r */\rpublic class ");
        className.append(BaseUtil.getClassFieldName(tableName.getTableName(),true));
        className.append(" implements Serializable ");
        return className.toString();
    }

    /**
     * 获取import数据
     * @param columnsDataList 列信息
     * @return import数据
     * @author Guobin.Hu
     * @date 2019/3/14 15:11
     */
    private static String getImport(List<ColumnsData> columnsDataList){
        StringBuffer importFile = new StringBuffer();
        Boolean date = false;
        Boolean blod = false;
        Boolean string = false;
        Boolean notNull = false;
        for (ColumnsData columnsData : columnsDataList) {
            if("Date".equals(columnsData.getColumnsType())){
                date = true;
            }else if("Blod".equals(columnsData.getColumnsType())){
                blod = true;
            }else if("String".equals(columnsData.getColumnsType())){
                string = true;
            }
            if(!columnsData.getNull()){
                notNull = true;
            }
        }
        if(notNull){
            importFile.append("import javax.validation.constraints.NotNull;\r");
        }
        if(string){
            importFile.append("import org.hibernate.validator.constraints.Length;\r");
        }
        if(blod){
            importFile.append("import java.sql.Blob;\r");
        }
        if(date){
            importFile.append("import java.util.Date;\r");
            importFile.append("import com.fasterxml.jackson.annotation.JsonFormat;\r");
        }
        importFile.append("import java.io.Serializable;\r");
        return importFile.toString();
    }

    /**
     * 获取POJO类package
     * @param
     * @return
     * @author Guobin.Hu
     * @date 2019/3/14 15:03
     */
    private static String getPackage(String packageName){
        StringBuffer classPackageFile = new StringBuffer();
        classPackageFile.append("package ");
        classPackageFile.append(packageName);
        classPackageFile.append(";\r");
        return classPackageFile.toString();
    }
}
