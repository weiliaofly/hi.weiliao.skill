package com.hi.weiliao.skill.service.impl;

import hi.weiliao.skill.dao.IUserDao;
import com.hi.weiliao.skill.service.IUserService;
import hi.weiliao.skill.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    IUserDao iUserDao;

    @Override
    public Map<String, String> login(User user) {
        Map<String, String> result = new HashMap<>();
        result.put("code", "200");
        result.put("message", "success");
        return result;
    }

    @Override
    public User selectByPrimaryKey(String id) {
        return iUserDao.selectByPrimaryKey(id);
    }
}
