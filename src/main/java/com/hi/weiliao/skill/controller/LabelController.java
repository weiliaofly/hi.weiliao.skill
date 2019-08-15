package com.hi.weiliao.skill.controller;

import com.hi.weiliao.skill.service.ILabelService;
import com.hi.weiliao.skill.utils.DateUtils;
import com.hi.weiliao.skill.vo.Label;
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

@Controller
@RequestMapping(value = "/label")
public class LabelController {

    private static final Logger logger = LoggerFactory.getLogger(LabelController.class);

    @Autowired
    private ILabelService labelService;

    @RequestMapping(value = "/find/{pageSize}/{pageIndex}", method = RequestMethod.GET)
    public @ResponseBody List<Label> findPage() {
        return labelService.query();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody boolean create(@RequestBody Label Label) {
        String now = DateUtils.currentTimeString(DateUtils.YYYYMMDDHHMISS);
        Label.setCreateDate(now);
        Label.setLastUpdateDate(now);
        return labelService.create(Label);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody boolean update(@RequestBody Label Label) {
        if(StringUtils.isBlank(Label.getId())){
            return false;
        }
        String now = DateUtils.currentTimeString(DateUtils.YYYYMMDDHHMISS);
        Label.setLastUpdateDate(now);
        return labelService.update(Label);
    }
}
