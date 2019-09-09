package com.hi.weiliao.skill.controller;

import com.hi.weiliao.skill.service.IArticleService;
import com.hi.weiliao.skill.utils.FastDFSClient;
import com.hi.weiliao.skill.vo.common.ResponseBean;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;

@Controller
@RequestMapping(value = "/file")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FastDFSClient fastDFSClient;

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public @ResponseBody ResponseBean findPage(File file) {
        try {
            InputStream inputStream = new FileInputStream(file);
            String url = fastDFSClient.uploadFile(IOUtils.toByteArray(inputStream));
            return new ResponseBean(ResponseBean.SUCCESS_CODE, ResponseBean.SUCCESS, url);
        }catch (Exception e){
            logger.error("Upload file error:", e);
            return new ResponseBean(ResponseBean.FAIL_CODE, ResponseBean.FAIL);
        }
    }
}
