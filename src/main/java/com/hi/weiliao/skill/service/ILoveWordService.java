package com.hi.weiliao.skill.service;

import com.hi.weiliao.skill.vo.LoveWord;
import com.hi.weiliao.skill.vo.PageBean;

import java.util.Map;

public interface ILoveWordService{

    PageBean<LoveWord> query(PageBean<LoveWord> pageBean, Map<String, Object> param);

    LoveWord findOne(Map<String, Object> param);

    boolean create(LoveWord loveWord);

    boolean update(LoveWord loveWord);
}
