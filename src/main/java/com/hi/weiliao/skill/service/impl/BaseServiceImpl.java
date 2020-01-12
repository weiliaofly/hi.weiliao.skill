package com.hi.weiliao.skill.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hi.weiliao.skill.service.IBaseService;
import com.hi.weiliao.skill.utils.MongoUtils;
import com.hi.weiliao.skill.vo.common.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class BaseServiceImpl<T> implements IBaseService<T> {

    @Autowired
    protected MongoTemplate mongoTemplate;

    protected Class<T> clazz;

    public BaseServiceImpl(){
        this.clazz = getSuperClassGenericType(this.getClass());
    }

    @Override
    public List<T> find(JSONObject json){
        Criteria criteria = MongoUtils.buildQuery(json);
        List<T> datas = mongoTemplate.find(new Query(criteria), clazz);

        return datas;
    }

    @Override
    public PageBean<T> find(PageBean<T> pageBean, JSONObject json){
        Criteria criteria = MongoUtils.buildQuery(json);
        Query query = MongoUtils.buildQueryByPage(json, pageBean);
        long total = mongoTemplate.count(new Query(criteria), clazz);
        Sort sort = new Sort(Sort.Direction.DESC, "lastUpdateDate");
        List<T> datas = mongoTemplate.find(query.with(sort), clazz);

        pageBean.setDatas(datas);
        pageBean.setTotal(total);
        pageBean.setPageNum(new Double(Math.ceil(total*1.0/pageBean.getPageSxize())).intValue());

        return pageBean;
    }

    @Override
    public T findOne(JSONObject json) {
        Criteria criteria = MongoUtils.buildQuery(json);
        T datas = mongoTemplate.findOne(new Query(criteria), clazz);
        return datas;
    }

    @Override
    public T findById(String id) {
        return mongoTemplate.findById(id, clazz);
    }

    @Override
    public void save(T data) {
        mongoTemplate.save(data);
    }

    @Override
    public void delete(String id) {
        Criteria criteria = new Criteria();
        criteria.and("_id").is(id);
        mongoTemplate.remove(new Query(criteria), clazz);
    }

    @Override
    public void delete(List<String> ids) {
        Criteria criteria = new Criteria();
        criteria.and("_id").in(ids);
        mongoTemplate.remove(new Query(criteria), clazz);
    }

    @Override
    public Long count(JSONObject param) {
        Criteria criteria = MongoUtils.buildQuery(param);
        return mongoTemplate.count(new Query(criteria), clazz);
    }

    public Class getSuperClassGenericType(Class clazz) {
        Type genType = clazz.getGenericSuperclass();// 得到泛型父类
        // 如果没有实现ParameterizedType接口，即不支持泛型，直接返回Object.class
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        // 返回表示此类型实际类型参数的Type对象的数组,数组里放的都是对应类型的Class, 如BuyerServiceBean extends
        Type[] parameters = ((ParameterizedType) genType).getActualTypeArguments();
        if (!(parameters[0] instanceof Class)) {
            return Object.class;
        }
        return (Class) parameters[0];
    }
}
