package com.hi.weiliao.skill.service.impl;

import com.hi.weiliao.skill.service.ILabelGroupService;
import com.hi.weiliao.skill.service.ILoveWordService;
import com.hi.weiliao.skill.utils.DateUtils;
import com.hi.weiliao.skill.vo.LabelGroup;
import com.hi.weiliao.skill.vo.LoveWord;
import com.mongodb.QueryBuilder;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LabelGroupServiceImpl implements ILabelGroupService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<LabelGroup> query(Map<String, Object> param) {
        Criteria criteria = new Criteria();
        param.forEach((K, V) -> {
            criteria.and(K).is(V);
        });

        return mongoTemplate.find(new Query(criteria), LabelGroup.class);
    }

    @Override
    public boolean create(LabelGroup labelGroup) {
        if(StringUtils.isBlank(labelGroup.getGroupName())){
            return false;
        }
        mongoTemplate.save(labelGroup);
        return true;
    }

    @Override
    public boolean update(LabelGroup labelGroup) {
        if(StringUtils.isBlank(labelGroup.getId())){
            return false;
        }
        Criteria criteria = Criteria.where("_id").is(labelGroup.getId());
        LabelGroup his = mongoTemplate.findOne(new Query(criteria), LabelGroup.class);
        if(his == null){
            return false;
        }
        labelGroup.setCreator(his.getCreator());
        labelGroup.setCreateDate(his.getCreateDate());
        labelGroup.setLastUpdateDate(DateUtils.currentTimeString(DateUtils.YYYYMMDDHHMISS));

        mongoTemplate.save(labelGroup);
        return true;
    }
}
