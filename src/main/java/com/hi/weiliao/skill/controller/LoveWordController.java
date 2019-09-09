package com.hi.weiliao.skill.controller;

import com.alibaba.fastjson.JSON;
import com.hi.weiliao.skill.service.ILoveWordService;
import com.hi.weiliao.skill.service.IOperateService;
import com.hi.weiliao.skill.utils.DateUtils;
import com.hi.weiliao.skill.utils.MapUtils;
import com.hi.weiliao.skill.vo.LoveWord;
import com.hi.weiliao.skill.vo.Operate;
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
@RequestMapping(value = "/loveword")
public class LoveWordController {

    private static final Logger logger = LoggerFactory.getLogger(LoveWordController.class);

    @Autowired
    private ILoveWordService loveWordService;

    @Autowired
    private IOperateService operateService;

    @RequestMapping(value = "/find/{pageSize}/{pageIndex}", method = RequestMethod.GET)
    public @ResponseBody
    PageBean<LoveWord> findPage(LoveWord loveWord, @PathVariable Integer pageSize,  @PathVariable Integer pageIndex) {
        Map<String, Object> param = JSON.parseObject(JSON.toJSONString(loveWord));
        logger.info("LoveWord: Query data by param ===>" + JSON.toJSONString(param));

        return loveWordService.query(new PageBean<>(pageIndex, pageSize), param);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody ResponseBean create(@RequestBody List<LoveWord> loveWords) {
        String now = DateUtils.currentTimeString(DateUtils.YYYYMMDDHHMISS);
        for (LoveWord loveWord : loveWords) {
            loveWord.setCreateDate(now);
            loveWord.setLastUpdateDate(now);
            loveWordService.create(loveWord);
        }
        logger.info("LoveWord: Create data ===> " + JSON.toJSONString(loveWords));
        return new ResponseBean();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody ResponseBean update(@RequestBody LoveWord loveWord) {
        if(StringUtils.isBlank(loveWord.getId())){
            return new ResponseBean(ResponseBean.PARAM_ERROR_CODE, "Id cant been null!");
        }
        logger.info("LoveWord: Update data ===> " + JSON.toJSONString(loveWord));
        loveWordService.update(loveWord);
        return new ResponseBean();
    }

    /**
     * 保存操作
     * @param id 话术ID
     * @param operate 操作 0-拷贝 1-收藏 2-点赞
     * @return
     */
    @RequestMapping(value = "/operate/{id}/{operate}", method = RequestMethod.GET)
    public @ResponseBody ResponseBean operate(@PathVariable String id, @PathVariable Integer operate, String userId) {
        String now = DateUtils.currentTimeString(DateUtils.YYYYMMDDHHMISS);
        LoveWord loveWord = loveWordService.findById(id);
        if(loveWord == null){
            return new ResponseBean(ResponseBean.FAIL_CODE, "This love word is not exist!");
        }

        if(operate != 0){
            Operate oper = new Operate("loveWord", id, operate, userId);
            Operate his = operateService.findOne(JSON.parseObject(JSON.toJSONString(oper)));
            if(his == null){
                oper.setCreateDate(now);
                operateService.save(oper);
            }else {
                operateService.delete(his.getId());
            }
        }

        switch (operate){
            case 0:
                loveWord.setCopyNum(loveWord.getCopyNum() + 1);
                break;
            case 1:
                loveWord.setComment(loveWord.getComment() + 1);
                break;
            case 2:
                loveWord.setAbulous(loveWord.getAbulous() + 1);
                break;
            default:
                break;
        }

        loveWord.setLastUpdateDate(now);
        loveWordService.update(loveWord);
        return new ResponseBean();
    }

    /**
     * 删除土味情话
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public @ResponseBody ResponseBean operate(List<String> ids) {
        loveWordService.delete(ids);
        return new ResponseBean();
    }
}
