package com.hi.weiliao.skill.service;

import com.hi.weiliao.skill.vo.LabelGroup;
import com.hi.weiliao.skill.vo.common.PageBean;

import java.util.Map;

public interface ILabelGroupService {

    PageBean<LabelGroup> query(PageBean<LabelGroup> pageBean, Map<String, Object> param);

    boolean create(LabelGroup labelGroup);

    boolean update(LabelGroup labelGroup);
}
