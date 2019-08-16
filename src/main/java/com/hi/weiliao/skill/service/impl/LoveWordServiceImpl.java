package com.hi.weiliao.skill.service.impl;

import com.hi.weiliao.skill.service.ILoveWordService;
import com.hi.weiliao.skill.vo.LoveWord;
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
            criteria.and(K).is(V);
        });

        return mongoTemplate.find(new Query(criteria), LoveWord.class);
    }

    @Override
    public boolean create(LoveWord loveWord) {
        mongoTemplate.save(loveWord);
        return true;
    }
}
