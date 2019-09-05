package com.hi.weiliao.skill.service.impl;

import com.hi.weiliao.skill.service.IBaseService;
import com.hi.weiliao.skill.vo.PageBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class BaseServiceImpl<T> implements IBaseService<T> {

    @Autowired
    private MongoTemplate mongoTemplate;

    private Class<T> clazz;

    public BaseServiceImpl(){
        this.clazz = getSuperClassGenericType(this.getClass(), 0);
    }

    @Override
    public PageBean<T> find(PageBean<T> pageBean, Map<String, Object> param){
        Criteria criteria = new Criteria();
        param.forEach((K, V) -> {
            if(StringUtils.isBlank(K) || StringUtils.isBlank(V.toString()) ){
                return;
            }
            criteria.and(K).is(V);
        });
        long total = mongoTemplate.count(new Query(criteria), clazz);
        List<T> datas = mongoTemplate.find(
                new Query(criteria).skip(pageBean.getPageSxize() * (pageBean.getPageIndex() - 1)).limit(pageBean.getPageSxize()),
                clazz);

        pageBean.setDatas(datas);
        pageBean.setTotal(total);
        pageBean.setPageNum(new Double(Math.ceil(total*1.0/pageBean.getPageSxize())).intValue());

        return pageBean;
    }

    @Override
    public T findOne(Map<String, Object> param) {
        Criteria criteria = new Criteria();
        param.forEach((K, V) -> {
            if(StringUtils.isBlank(K) || StringUtils.isBlank(V.toString()) ){
                return;
            }
            criteria.and(K).is(V);
        });
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
        criteria.where("_id").is(id);
        mongoTemplate.remove(new Query(criteria), clazz);
    }

    public static Class getSuperClassGenericType(Class clazz, int index) {
        Type genType = clazz.getGenericSuperclass();// 得到泛型父类
        // 如果没有实现ParameterizedType接口，即不支持泛型，直接返回Object.class
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        // 返回表示此类型实际类型参数的Type对象的数组,数组里放的都是对应类型的Class, 如BuyerServiceBean extends
        // DaoSupport就返回Buyer和Contact类型
        Type[] parameters = ((ParameterizedType) genType).getActualTypeArguments();
        if (index > parameters.length || index < 0) {
            throw new RuntimeException("你输入的索引号" + (index < 0 ? "不能小于0" : "超出了参数的总数"));
        }
        if (!(parameters[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) parameters[index];
    }
}
