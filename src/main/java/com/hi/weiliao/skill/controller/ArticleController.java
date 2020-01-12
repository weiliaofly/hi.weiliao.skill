package com.hi.weiliao.skill.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hi.weiliao.skill.service.IArticleService;
import com.hi.weiliao.skill.service.IOperateService;
import com.hi.weiliao.skill.service.ICommentService;
import com.hi.weiliao.skill.utils.DateUtils;
import com.hi.weiliao.skill.vo.Article;
import com.hi.weiliao.skill.vo.LoveWord;
import com.hi.weiliao.skill.vo.Operate;
import com.hi.weiliao.skill.vo.Comment;
import com.hi.weiliao.skill.vo.common.PageBean;
import com.hi.weiliao.skill.vo.common.ResponseBean;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.List;

@Controller
@RequestMapping(value = "/article")
public class ArticleController {

    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    private IArticleService articleService;

    @Autowired
    private ICommentService commentService;

    @Autowired
    private IOperateService operateService;

    @RequestMapping(value = "/find/{pageSize}/{pageIndex}", method = RequestMethod.GET)
    public @ResponseBody
    PageBean<Article> findPage(Article article, @PathVariable Integer pageSize, @PathVariable Integer pageIndex) {
        JSONObject param = JSON.parseObject(JSON.toJSONString(article));
        if(StringUtils.isNotBlank(article.getTitle())){
            JSONObject title = new JSONObject();
            title.put("$regex", article.getTitle());
            param.put("title", title);
        }

        logger.info("Article: Query data by param ===>" + JSON.toJSONString(param));

        if(StringUtils.isNotBlank(article.getTitle())){
            JSONObject title = new JSONObject();
            title.put("$regex", article.getTitle());
            param.put("title", title);
        }
        PageBean<Article> page = articleService.find(new PageBean<>(pageIndex, pageSize), param);
        List<Article> list = page.getDatas();

        for (Article item: list) {
            item.setContent(null);
        }
        page.setDatas(list);

        return page;
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Article findPage(@PathVariable String id, String userId) {
        Article article = articleService.findById(id);
        JSONObject query = new JSONObject();
        query.put("article", article.getId());

        article.setView(article.getView() == null? 1: article.getView() + 1);
        articleService.save(article);

        Long count = commentService.count(query);
        article.setComment(count.intValue());

        query.clear();
        query.put("objectId", article.getId());
        query.put("object", "article");
        query.put("operate", 1);
        query.put("creator", userId);
        Operate operates = operateService.findOne(query);
        article.setIsCollected(operates != null);

        return article;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody
    ResponseBean create(@RequestBody Article article) {
        String name = UUID.randomUUID().toString();
        String now = DateUtils.currentTimeString(DateUtils.YYYYMMDDHHMISS);
        article.setId(name);
        article.setArticleName(name);
        article.setCreateDate(now);
        article.setLastUpdateDate(now);

        initArtical(article);

        logger.info("Article: Create data ===> " + JSON.toJSONString(article));
        articleService.save(article);

        JSONObject param = new JSONObject();
        param.put("articleName", name);
        ResponseBean responseBean = new ResponseBean();

        responseBean.setResults(articleService.findOne(param));
        return responseBean;
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
        articleService.delete(idList);
        return new ResponseBean();
    }

    /**
     * 保存操作
     * @param id 话术ID
     * @param operate 操作 0-转发 1-收藏 2-点赞 3-喜欢
     * @return
     */
    @RequestMapping(value = "/operate/{id}/{operate}", method = RequestMethod.GET)
    public @ResponseBody ResponseBean operate(@PathVariable String id, @PathVariable Integer operate, String userId) {
        String now = DateUtils.currentTimeString(DateUtils.YYYYMMDDHHMISS);
        Article article = articleService.findById(id);
        if(article == null){
            return new ResponseBean(ResponseBean.FAIL_CODE, "This article is not exist!");
        }

        if(operate != 0){
            Operate oper = new Operate("article", id, operate, userId);
            Operate his = operateService.findOne(JSON.parseObject(JSON.toJSONString(oper)));
            if(his == null){
                oper.setCreateDate(now);
                operateService.save(oper);
            }else {
                operateService.delete(his.getId());
                return new ResponseBean();
            }
        }

        switch (operate){
            case 0:
                article.setForward(article.getForward() == null? 1:article.getForward() + 1);
                break;
            case 1:
                article.setCollection(article.getCollection() == null? 1:article.getCollection() + 1);
                break;
            case 2:
                article.setAbulous(article.getAbulous() == null? 1:article.getAbulous() + 1);
                break;
            case 3:
                article.setLike(article.getLike() == null? 1:article.getLike() + 1);
                break;
            default:
                break;
        }

        article.setLastUpdateDate(now);
        articleService.save(article);
        return new ResponseBean();
    }

    private void initArtical(Article article){
        article.setLike(0);
        article.setAbulous(0);
        article.setCollection(0);
        article.setComment(0);
        article.setForward(0);
        article.setView(0);
        article.setType(article.getType() == null? 3: article.getType());
        article.setStatus(article.getStatus() == null? 0: article.getStatus());
    }



    /**
     * 操作列表查询
     * @param operate 操作 0-转发 1-收藏 2-点赞 3-喜欢
     * @return
     */
    @RequestMapping(value = "/operate/list/{operate}/{pageSize}/{pageIndex}", method = RequestMethod.GET)
    public @ResponseBody ResponseBean operate(@PathVariable Integer operate, @PathVariable Integer pageSize,
                                              @PathVariable Integer pageIndex, String userId) {
        JSONObject param = new JSONObject();
        param.put("object", "article");
        param.put("operate", operate);
        if(StringUtils.isNotBlank(userId)){
            param.put("creator", userId);
        }
        List<Operate> operates = operateService.find(param);

        List<String> ids = new ArrayList<>();
        operates.forEach(item -> {
            ids.add(item.getObjectId());
        });

        JSONObject in = new JSONObject();
        JSONObject query = new JSONObject();
        in.put("$in", ids);
        query.put("id", in);
        PageBean<Article> articles = articleService.find(new PageBean<>(pageIndex, pageSize), query);
        for (Article item: articles.getDatas()) {
            item.setContent(null);
        }
        return new ResponseBean(ResponseBean.SUCCESS_CODE, ResponseBean.SUCCESS, articles);
    }
}
