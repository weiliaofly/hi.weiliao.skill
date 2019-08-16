package com.hi.weiliao.skill.service;

import com.hi.weiliao.skill.vo.LabelGroup;
import com.hi.weiliao.skill.vo.LoveWord;

import java.util.List;
import java.util.Map;

public interface ILabelGroupService {

    List<LabelGroup> query(Map<String, Object> param);

    boolean create(LabelGroup labelGroup);

    boolean update(LabelGroup labelGroup);
}
