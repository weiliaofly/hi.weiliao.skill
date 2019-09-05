package com.hi.weiliao.skill.service;

import com.hi.weiliao.skill.vo.PageBean;

import java.util.Map;

public interface IBaseService<T> {

    /**
     * 分页查询
     * @param pageBean 分页参数
     * @param param 查询参数
     * @return
     */
    PageBean<T> find(PageBean<T> pageBean, Map<String, Object> param);

    /**
     * 查询单条数据
     * @param param 查询参数
     * @return
     */
     T findOne(Map<String, Object> param);

    /**
     * 根据ID查询
     * @param id 查询参数
     * @return
     */
     T findById(String id);

    /**
     * 保存数据
     * @param data 保存的数据
     * @return
     */
     void save(T data);

    /**
     * 删除数据
     * @param id 根据ID删除
     * @return
     */
     void delete(String id);

}
