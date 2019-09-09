package com.hi.weiliao.skill.service.impl;

import com.hi.weiliao.skill.service.ILoveWordService;
import com.hi.weiliao.skill.utils.DateUtils;
import com.hi.weiliao.skill.vo.LoveWord;
import com.hi.weiliao.skill.vo.common.PageBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LoveWordServiceImpl extends BaseServiceImpl<LoveWord> implements ILoveWordService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public PageBean<LoveWord> query(PageBean<LoveWord> pageBean, Map<String, Object> param) {
        Criteria criteria = new Criteria();
        param.forEach((K, V) -> {
            if(StringUtils.isBlank(K) || StringUtils.isBlank(V.toString()) ){
                return;
            }
            if(StringUtils.isNotBlank(K) && K.equals("skillContent")){
                criteria.and(K).regex(".*" + V + ".*");
                return;
            }
            criteria.and(K).is(V);
        });
        long total = mongoTemplate.count(new Query(criteria), LoveWord.class);
        List<LoveWord> datas = mongoTemplate.find(
                new Query(criteria).skip(pageBean.getPageSxize() * (pageBean.getPageIndex() - 1)).limit(pageBean.getPageSxize()),
                LoveWord.class);

        pageBean.setDatas(datas);
        pageBean.setTotal(total);
        pageBean.setPageNum(new Double(Math.ceil(total*1.0/pageBean.getPageSxize())).intValue());

        return pageBean;
    }

    @Override
    public LoveWord findOne(Map<String, Object> param) {
        Criteria criteria = new Criteria();
        param.forEach((K, V) -> {
            if(StringUtils.isBlank(K) || StringUtils.isBlank(V.toString()) ){
                return;
            }
            if(StringUtils.isNotBlank(K) && K.equals("skillContent")){
                criteria.and(K).regex(".*" + V + ".*");
                return;
            }
            criteria.and(K).is(V);
        });
        return mongoTemplate.findOne(new Query(criteria), LoveWord.class);
    }

    @Override
    public boolean create(LoveWord loveWord) {
        mongoTemplate.save(loveWord);
        return true;
    }

    @Override
    public boolean update(LoveWord loveWord) {
        if(StringUtils.isBlank(loveWord.getId())){
            return false;
        }
        Criteria criteria = Criteria.where("_id").is(loveWord.getId());
        LoveWord his = mongoTemplate.findOne(new Query(criteria), LoveWord.class);
        if(his == null){
            return false;
        }
        loveWord.setCreator(his.getCreator());
        loveWord.setCreateDate(his.getCreateDate());
        loveWord.setLastUpdateDate(DateUtils.currentTimeString(DateUtils.YYYYMMDDHHMISS));

        mongoTemplate.save(loveWord);
        return true;
    }
}
