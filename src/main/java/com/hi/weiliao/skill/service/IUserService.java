package com.hi.weiliao.skill.service;

import hi.weiliao.skill.vo.User;

import java.util.Map;

public interface IUserService{

    Map<String, String> login(User user);

    User selectByPrimaryKey(String id);
}
