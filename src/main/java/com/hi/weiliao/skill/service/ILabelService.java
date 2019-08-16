package com.hi.weiliao.skill.service;

import com.hi.weiliao.skill.vo.Label;
import com.hi.weiliao.skill.vo.LabelGroup;

import java.util.List;
import java.util.Map;

public interface ILabelService {

    List<Label> query(Map<String, Object> param);

    boolean create(Label label);

    boolean update(Label label);
}
