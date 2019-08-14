package com.hi.weiliao.skill.service.impl;

import com.hi.weiliao.skill.service.ILoveWordService;
import com.hi.weiliao.skill.vo.LoveWord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoveWordServiceImpl implements ILoveWordService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<LoveWord> query() {
        return mongoTemplate.find(new Query(), LoveWord.class);
    }
}
