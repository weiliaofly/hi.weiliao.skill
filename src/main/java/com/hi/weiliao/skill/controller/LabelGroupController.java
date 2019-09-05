package com.hi.weiliao.skill.controller;

import com.alibaba.fastjson.JSON;
import com.hi.weiliao.skill.service.ILabelGroupService;
import com.hi.weiliao.skill.service.ILoveWordService;
import com.hi.weiliao.skill.utils.DateUtils;
import com.hi.weiliao.skill.vo.LabelGroup;
import com.hi.weiliao.skill.vo.LoveWord;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/labelgroup")
public class LabelGroupController {

    private static final Logger logger = LoggerFactory.getLogger(LabelGroupController.class);

    @Autowired
    private ILabelGroupService labelGroupService;

    @RequestMapping(value = "/find/{pageSize}/{pageIndex}", method = RequestMethod.GET)
    public @ResponseBody List<LabelGroup> findPage(LabelGroup labelGroup) {
        Map<String, Object> param = JSON.parseObject(JSON.toJSONString(labelGroup));
        return labelGroupService.query(param);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody boolean create(@RequestBody LabelGroup labelGroup) {
        String now = DateUtils.currentTimeString(DateUtils.YYYYMMDDHHMISS);
        labelGroup.setCreateDate(now);
        labelGroup.setLastUpdateDate(now);
        return labelGroupService.create(labelGroup);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody boolean update(@RequestBody LabelGroup labelGroup) {
        if(StringUtils.isBlank(labelGroup.getId())){
            return false;
        }
        String now = DateUtils.currentTimeString(DateUtils.YYYYMMDDHHMISS);
        labelGroup.setLastUpdateDate(now);
        return labelGroupService.update(labelGroup);
    }
}
