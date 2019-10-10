package com.hgb.utils.controller;

import com.hgb.utils.getjava.DbToJavaUtil;
import com.hgb.utils.getjava.bean.request.DbRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Title: DbToJavaController
 * @ProjectName util
 * @Description: TODO
 * @Author Guobin.Hu
 * @Date 2019/4/28 9:49
 */
@RestController
@RequestMapping("/java")
public class DbToJavaController {

    /**
     * 数据库转java文件
     * @param
     * @return
     * @author Guobin.Hu
     * @date 2019/4/28 9:56
     */
    @PostMapping("/toJava")
    public void dbToJava(@RequestBody @Validated DbRequest request){
        DbToJavaUtil.getJavaFile(request);
    }

}
