package com.hi.weiliao.skill.controller;

import com.alibaba.fastjson.JSON;
import com.hi.weiliao.skill.service.ILabelGroupService;
import com.hi.weiliao.skill.service.ILabelService;
import com.hi.weiliao.skill.service.ILoveWordService;
import com.hi.weiliao.skill.utils.DateUtils;
import com.hi.weiliao.skill.vo.LabelGroup;
import com.hi.weiliao.skill.vo.LoveWord;
import com.hi.weiliao.skill.vo.PageBean;
import com.hi.weiliao.skill.vo.ResponseBean;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/labelgroup")
public class LabelGroupController {

    private static final Logger logger = LoggerFactory.getLogger(LabelGroupController.class);

    @Autowired
    private ILabelGroupService labelGroupService;

    @Autowired
    private ILabelService labelService;

    @RequestMapping(value = "/find/{pageSize}/{pageIndex}", method = RequestMethod.GET)
    public @ResponseBody
    PageBean<LabelGroup> findPage(LabelGroup labelGroup, @PathVariable Integer pageSize, @PathVariable Integer pageIndex) {
        Map<String, Object> param = JSON.parseObject(JSON.toJSONString(labelGroup));
        logger.info("LabelGroup: Query data by param ===>" + JSON.toJSONString(param));
        PageBean<LabelGroup> pageBean = labelGroupService.query(new PageBean<>(pageIndex, pageSize), param);
        List<LabelGroup> datas = pageBean.getDatas();

        if(datas == null || datas.size() == 0){
            return pageBean;
        }

        Map<String, Object> query = new HashMap<>();
        for (LabelGroup group : datas) {
            query.put("labelGroup", group.getId());
            group.setLabels(labelService.findList(query));
        }
        pageBean.setDatas(datas);

        return pageBean;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody ResponseBean create(@RequestBody LabelGroup labelGroup) {
        String now = DateUtils.currentTimeString(DateUtils.YYYYMMDDHHMISS);
        labelGroup.setCreateDate(now);
        labelGroup.setLastUpdateDate(now);

        logger.info("LabelGroup: Create data ===> " + JSON.toJSONString(labelGroup));
        labelGroupService.create(labelGroup);
        return new ResponseBean();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody ResponseBean update(@RequestBody LabelGroup labelGroup) {
        if(StringUtils.isBlank(labelGroup.getId())){
            return new ResponseBean(ResponseBean.PARAM_ERROR_CODE, "Id cant been null!");
        }
        String now = DateUtils.currentTimeString(DateUtils.YYYYMMDDHHMISS);
        labelGroup.setLastUpdateDate(now);

        logger.info("LabelGroup: Update data ===> " + JSON.toJSONString(labelGroup));
        labelGroupService.update(labelGroup);
        return new ResponseBean();
    }
}
