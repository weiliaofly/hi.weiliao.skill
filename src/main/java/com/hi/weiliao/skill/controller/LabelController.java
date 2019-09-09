package com.hi.weiliao.skill.controller;

import com.alibaba.fastjson.JSON;
import com.hi.weiliao.skill.service.ILabelService;
import com.hi.weiliao.skill.utils.DateUtils;
import com.hi.weiliao.skill.vo.Label;
import com.hi.weiliao.skill.vo.common.PageBean;
import com.hi.weiliao.skill.vo.common.ResponseBean;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping(value = "/label")
public class LabelController {

    private static final Logger logger = LoggerFactory.getLogger(LabelController.class);

    @Autowired
    private ILabelService labelService;

    @RequestMapping(value = "/find/{pageSize}/{pageIndex}", method = RequestMethod.GET)
    public @ResponseBody
    PageBean<Label> findPage(Label label, @PathVariable Integer pageSize, @PathVariable Integer pageIndex) {
        Map<String, Object> param = JSON.parseObject(JSON.toJSONString(label));
        logger.info("Label: Query data by param ===>" + JSON.toJSONString(param));
        return labelService.query(new PageBean<>(pageIndex, pageSize), param);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody
    ResponseBean create(@RequestBody Label label) {
        String now = DateUtils.currentTimeString(DateUtils.YYYYMMDDHHMISS);
        label.setCreateDate(now);
        label.setLastUpdateDate(now);

        logger.info("Label: Create data ===> " + JSON.toJSONString(label));
        labelService.create(label);
        return new ResponseBean();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody ResponseBean update(@RequestBody Label label) {
        if(StringUtils.isBlank(label.getId())){
            return new ResponseBean(ResponseBean.PARAM_ERROR_CODE, "Id cant been null!");
        }
        String now = DateUtils.currentTimeString(DateUtils.YYYYMMDDHHMISS);
        label.setLastUpdateDate(now);

        logger.info("Label: Update data ===> " + JSON.toJSONString(label));
        labelService.update(label);
        return new ResponseBean();
    }
}
