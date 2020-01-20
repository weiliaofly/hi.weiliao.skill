package com.hi.weiliao.skill.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hi.weiliao.skill.service.ICommentService;
import com.hi.weiliao.skill.utils.DateUtils;
import com.hi.weiliao.skill.utils.HttpConnectionPoolUtil;
import com.hi.weiliao.skill.utils.HttpUtils;
import com.hi.weiliao.skill.vo.Comment;
import com.hi.weiliao.skill.vo.common.PageBean;
import com.hi.weiliao.skill.vo.common.ResponseBean;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping(value = "/comment")
public class CommentController {

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private ICommentService commentService;

    @RequestMapping(value = "/find/{pageSize}/{pageIndex}", method = RequestMethod.GET)
    public @ResponseBody
    PageBean<Comment> findPage(Comment comment, @PathVariable Integer pageSize, @PathVariable Integer pageIndex) {
        JSONObject param = JSON.parseObject(JSON.toJSONString(comment));
        logger.info("comment: Query data by param ===>" + JSON.toJSONString(param));

        if(StringUtils.isNotBlank(comment.getContent())){
            JSONObject content = new JSONObject();
            content.put("$regex", comment.getContent());
            param.put("title", content);
        }
        PageBean<Comment> pageBean = commentService.find(new PageBean<>(pageIndex, pageSize), param);
        return pageBean;
    }

    private boolean verifyLegality(String content, HttpServletRequest request){
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", request.getHeader("Authorization"));
        header.put("Content-Type", "application/json");
        String param = "{\"content\": \""+ content +"\"}";
        try {
            String result = HttpConnectionPoolUtil.doPost("https://www.imlmbm.xyz/weiliao/gateway/wechat/msg_sec_check", header, param);
            if(StringUtils.isBlank(result) || JSON.parseObject(result).getInteger("code") != 200){
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody
    ResponseBean create(@RequestBody Comment comment, HttpServletRequest request) {
        if(!verifyLegality(comment.getContent(), request)){
            return new ResponseBean(ResponseBean.PARAM_ERROR_CODE, "Illegal content！");
        }

        String id = UUID.randomUUID().toString();
        comment.setId(id);
        String now = DateUtils.currentTimeString(DateUtils.YYYYMMDDHHMISS);
        comment.setCreateDate(now);
        comment.setLastUpdateDate(now);

        logger.info("comment: Create data ===> " + JSON.toJSONString(comment));
        commentService.save(comment);
        return new ResponseBean();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody ResponseBean update(@RequestBody Comment comment, HttpServletRequest request) {

        if(StringUtils.isBlank(comment.getId())){
            return new ResponseBean(ResponseBean.PARAM_ERROR_CODE, "Id cant been null!");
        }
        if(!verifyLegality(comment.getContent(), request)){
            return new ResponseBean(ResponseBean.PARAM_ERROR_CODE, "Illegal content！");
        }

        //查询旧数据
        Comment his = commentService.findById(comment.getId());
        String now = DateUtils.currentTimeString(DateUtils.YYYYMMDDHHMISS);
        his.setLastUpdateDate(now);
        his.setContent(comment.getContent());
        his.setStatus(comment.getStatus());

        logger.info("comment: Update data ===> " + JSON.toJSONString(comment));
        commentService.save(his);
        return new ResponseBean();
    }

    /**
     * 删除文章
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public @ResponseBody ResponseBean operate(String ids) {
        if(StringUtils.isBlank(ids)){
            return new ResponseBean(ResponseBean.FAIL_CODE, ResponseBean.FAIL + "IDS is null!");
        }
        List<String> idList = Arrays.asList(ids.split(","));
        commentService.delete(idList);
        return new ResponseBean();
    }
}
