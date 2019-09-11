package com.hi.weiliao.skill.controller;

import com.hi.weiliao.skill.service.IFileService;
import com.hi.weiliao.skill.utils.DateUtils;
import com.hi.weiliao.skill.utils.FastDFSClient;
import com.hi.weiliao.skill.vo.CustomerFile;
import com.hi.weiliao.skill.vo.common.ResponseBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/file")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FastDFSClient fastDFSClient;

    @Autowired
    private IFileService fileService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody ResponseBean findPage(@RequestParam("uploadFile") MultipartFile multipartFile) {
        String now = DateUtils.currentTimeString(DateUtils.YYYYMMDDHHMISS);
        try {
            String fileName = multipartFile.getOriginalFilename();
            String extName = fileName.substring(fileName.lastIndexOf(".") + 1);

            String url = fastDFSClient.uploadFile(multipartFile.getBytes(), extName);

            CustomerFile cf = new CustomerFile(fileName, extName, url, multipartFile.getSize()/1024);
            cf.setCreateDate(now);
            cf.setLastUpdateDate(now);
            cf.setCreator("FLMheart");
            fileService.save(cf);

            return new ResponseBean(ResponseBean.SUCCESS_CODE, ResponseBean.SUCCESS, url);
        }catch (Exception e){
            logger.error("Upload file error:", e);
            return new ResponseBean(ResponseBean.FAIL_CODE, e.getMessage());
        }
    }
}
