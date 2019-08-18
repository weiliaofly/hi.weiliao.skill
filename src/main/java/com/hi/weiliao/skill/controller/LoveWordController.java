package com.hi.weiliao.skill.controller;

import com.alibaba.fastjson.JSON;
import com.hi.weiliao.skill.service.ILoveWordService;
import com.hi.weiliao.skill.utils.DateUtils;
import com.hi.weiliao.skill.vo.LabelGroup;
import com.hi.weiliao.skill.vo.LoveWord;
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
@RequestMapping(value = "/loveword")
public class LoveWordController {

    private static final Logger logger = LoggerFactory.getLogger(LoveWordController.class);

    @Autowired
    private ILoveWordService loveWordService;

    @RequestMapping(value = "/find/{pageSize}/{pageIndex}", method = RequestMethod.GET)
    public @ResponseBody List<LoveWord> findPage(LoveWord loveWord) {
        Map<String, Object> param = JSON.parseObject(JSON.toJSONString(loveWord));
        return loveWordService.query(param);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody boolean create(@RequestBody LoveWord loveWord) {
        String now = DateUtils.currentTimeString(DateUtils.YYYYMMDDHHMISS);
        loveWord.setCreateDate(now);
        loveWord.setLastUpdateDate(now);
        return loveWordService.create(loveWord);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody boolean update(@RequestBody LoveWord loveWord) {
        return loveWordService.update(loveWord);
    }
}
