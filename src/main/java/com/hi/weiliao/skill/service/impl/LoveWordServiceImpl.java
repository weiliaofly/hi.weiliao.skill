package com.hi.weiliao.skill.service.impl;

import com.hi.weiliao.skill.service.ILoveWordService;
import com.hi.weiliao.skill.utils.DateUtils;
import com.hi.weiliao.skill.vo.Label;
import com.hi.weiliao.skill.vo.LoveWord;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LoveWordServiceImpl implements ILoveWordService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<LoveWord> query(Map<String, Object> param) {
        Criteria criteria = new Criteria();
        param.forEach((K, V) -> {
            if(StringUtils.isBlank(K) || StringUtils.isBlank(V.toString()) ){
                return;
            }
            criteria.and(K).is(V);
        });

        return mongoTemplate.find(new Query(criteria), LoveWord.class);
    }

    @Override
    public boolean create(LoveWord loveWord) {
        if(StringUtils.isBlank(loveWord.getSkillName())){
            return false;
        }
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
