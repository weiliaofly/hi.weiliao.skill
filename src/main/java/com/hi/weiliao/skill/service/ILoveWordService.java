package com.hi.weiliao.skill.service;

import com.hi.weiliao.skill.vo.LoveWord;

import java.util.List;
import java.util.Map;

public interface ILoveWordService{

    List<LoveWord> query(Map<String, Object> param);

    boolean create(LoveWord loveWord);
}
