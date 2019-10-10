package com.hgb.utils.getjava;

import java.io.*;
import java.util.Date;

/**
 * @Title: FileUtil
 * @ProjectName util
 * @Description: TODO
 * @Author Guobin.Hu
 * @Date 2019/3/14 16:50
 */
public class FileUtil implements Serializable{

    /**
     * 输出文件
     * @param fileName 文件名(带后缀)
     * @param fileContent 文件内容
     * @param path 文件生成路径
     * @return
     * @author Guobin.Hu
     * @date 2019/3/14 16:51
     */
    public static void outFile(String fileName,String fileContent,String path){
        File file = new File(path, fileName);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
