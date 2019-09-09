package com.hi.weiliao.skill.service;

import com.hi.weiliao.skill.vo.Label;
import com.hi.weiliao.skill.vo.common.PageBean;

import java.util.List;
import java.util.Map;

public interface ILabelService {

    PageBean<Label> query(PageBean<Label> pageBean, Map<String, Object> param);

    Label findOne(Map<String, Object> param);

    List<Label> findList(Map<String, Object> param);

    boolean create(Label label);

    boolean update(Label label);
}
