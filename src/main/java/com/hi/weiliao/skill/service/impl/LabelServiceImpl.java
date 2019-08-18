package com.hi.weiliao.skill.service.impl;

import com.hi.weiliao.skill.service.ILabelService;
import com.hi.weiliao.skill.utils.DateUtils;
import com.hi.weiliao.skill.vo.Label;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LabelServiceImpl implements ILabelService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Label> query(Map<String, Object> param) {
        Criteria criteria = new Criteria();
        param.forEach((K, V) -> {
            criteria.and(K).is(V);
        });

        return mongoTemplate.find(new Query(criteria), Label.class);
    }

    @Override
    public boolean create(Label label) {
        if(StringUtils.isBlank(label.getLabel())){
            return false;
        }
        mongoTemplate.save(label);
        return true;
    }

    @Override
    public boolean update(Label label) {
        if(StringUtils.isBlank(label.getId())){
            return false;
        }
        Criteria criteria = Criteria.where("_id").is(label.getId());
        Label his = mongoTemplate.findOne(new Query(criteria), Label.class);
        if(his == null){
            return false;
        }
        label.setCreator(his.getCreator());
        label.setCreateDate(his.getCreateDate());
        label.setLastUpdateDate(DateUtils.currentTimeString(DateUtils.YYYYMMDDHHMISS));

        mongoTemplate.save(label);
        return true;
    }
}
