package cn.cheng.crm.settings.dao;

import cn.cheng.crm.settings.domain.User;

import java.util.Map;


public interface UserDao {
    User login(Map<String, String> map);
}
