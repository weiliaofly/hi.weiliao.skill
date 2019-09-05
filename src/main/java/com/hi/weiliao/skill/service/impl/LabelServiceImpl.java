package com.hi.weiliao.skill.service.impl;

import com.hi.weiliao.skill.service.ILabelService;
import com.hi.weiliao.skill.utils.DateUtils;
import com.hi.weiliao.skill.vo.Label;
import com.hi.weiliao.skill.vo.LoveWord;
import com.hi.weiliao.skill.vo.PageBean;
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
    public PageBean<Label> query(PageBean<Label> pageBean, Map<String, Object> param) {
        Criteria criteria = new Criteria();
        param.forEach((K, V) -> {
            if(StringUtils.isBlank(K) || StringUtils.isBlank(V.toString()) ){
                return;
            }
            criteria.and(K).is(V);
        });
        long total = mongoTemplate.count(new Query(criteria), Label.class);
        List<Label> datas = mongoTemplate.find(
                new Query(criteria).skip(pageBean.getPageSxize() * (pageBean.getPageIndex() - 1)).limit(pageBean.getPageSxize()),
                Label.class);

        pageBean.setDatas(datas);
        pageBean.setTotal(total);
        pageBean.setPageNum(new Double(Math.ceil(total*1.0/pageBean.getPageSxize())).intValue());

        return pageBean;
    }

    @Override
    public Label findOne(Map<String, Object> param) {
        Criteria criteria = new Criteria();
        param.forEach((K, V) -> {
            if(StringUtils.isBlank(K) || StringUtils.isBlank(V.toString()) ){
                return;
            }
            criteria.and(K).is(V);
        });
        return mongoTemplate.findOne(new Query(criteria), Label.class);
    }

    @Override
    public List<Label> findList(Map<String, Object> param) {
        Criteria criteria = new Criteria();
        param.forEach((K, V) -> {
            if(StringUtils.isBlank(K) || StringUtils.isBlank(V.toString()) ){
                return;
            }
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
