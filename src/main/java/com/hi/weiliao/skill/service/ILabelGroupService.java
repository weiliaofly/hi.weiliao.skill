package com.hi.weiliao.skill.service;

import com.hi.weiliao.skill.vo.LabelGroup;
import com.hi.weiliao.skill.vo.LoveWord;

import java.util.List;

public interface ILabelGroupService {

    List<LabelGroup> query(LabelGroup labelGroup);

    boolean create(LabelGroup labelGroup);

    boolean update(LabelGroup labelGroup);
}
