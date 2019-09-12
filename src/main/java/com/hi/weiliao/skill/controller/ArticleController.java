package com.hi.weiliao.skill.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hi.weiliao.skill.service.IArticleService;
import com.hi.weiliao.skill.service.ICommentService;
import com.hi.weiliao.skill.utils.DateUtils;
import com.hi.weiliao.skill.vo.Article;
import com.hi.weiliao.skill.vo.Comment;
import com.hi.weiliao.skill.vo.common.PageBean;
import com.hi.weiliao.skill.vo.common.ResponseBean;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/article")
public class ArticleController {

    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    private IArticleService articleService;

    @Autowired
    private ICommentService commentService;

    @RequestMapping(value = "/find/{pageSize}/{pageIndex}", method = RequestMethod.GET)
    public @ResponseBody
    PageBean<Article> findPage(Article article, @PathVariable Integer pageSize, @PathVariable Integer pageIndex) {
        JSONObject param = JSON.parseObject(JSON.toJSONString(article));
        logger.info("Article: Query data by param ===>" + JSON.toJSONString(param));

        if(StringUtils.isNotBlank(article.getTitle())){
            JSONObject title = new JSONObject();
            title.put("$regex", article.getTitle());
            param.put("title", title);
        }
        PageBean<Article> page = articleService.find(new PageBean<>(pageIndex, pageSize), param);
        List<Article> list = page.getDatas();

        JSONObject query = new JSONObject();

        for (Article item: list) {
            query.put("article", item.getId());

            List<Comment> comments = commentService.find(query);
            item.setComments(comments);
        }
        page.setDatas(list);

        return page;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody
    ResponseBean create(@RequestBody Article article) {
        String now = DateUtils.currentTimeString(DateUtils.YYYYMMDDHHMISS);
        article.setCreateDate(now);
        article.setLastUpdateDate(now);

        logger.info("Article: Create data ===> " + JSON.toJSONString(article));
        articleService.save(article);
        return new ResponseBean();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody ResponseBean update(@RequestBody Article article) {
        if(StringUtils.isBlank(article.getId())){
            return new ResponseBean(ResponseBean.PARAM_ERROR_CODE, "Id cant been null!");
        }
        String now = DateUtils.currentTimeString(DateUtils.YYYYMMDDHHMISS);
        article.setLastUpdateDate(now);

        logger.info("Article: Update data ===> " + JSON.toJSONString(article));
        articleService.save(article);
        return new ResponseBean();
    }
}
