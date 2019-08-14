package com.hi.weiliao.skill.service.impl;

import com.hi.weiliao.skill.service.ILoveWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.stereotype.Service;

@Service
public class LoveWordServiceImpl implements ILoveWordService {

    @Autowired
    MongoDbFactory mongoDbFactory;
}
