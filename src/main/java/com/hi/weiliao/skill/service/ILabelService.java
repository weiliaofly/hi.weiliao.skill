package com.hi.weiliao.skill.service;

import com.hi.weiliao.skill.vo.Label;
import com.hi.weiliao.skill.vo.LabelGroup;

import java.util.List;

public interface ILabelService {

    List<Label> query();

    boolean create(Label label);

    boolean update(Label label);
}
