package com.hi.weiliao.skill.service;

import com.alibaba.fastjson.JSONObject;
import com.hi.weiliao.skill.vo.common.PageBean;

import java.util.List;

public interface IBaseService<T> {

    /**
     * 分页查询
     * @param pageBean 分页参数
     * @param param 查询参数
     * @return
     */
    PageBean<T> find(PageBean<T> pageBean, JSONObject param);

    /**
     * 分页查询
     * @param param 查询参数
     * @return
     */
    List<T> find(JSONObject param);

    /**
     * 查询单条数据
     * @param param 查询参数
     * @return
     */
     T findOne(JSONObject param);

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

    /**
     * 批量删除数据
     * @param ids 根据ID删除
     * @return
     */
     void delete(List<String> ids);

}
