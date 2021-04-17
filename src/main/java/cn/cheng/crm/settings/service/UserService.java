package cn.cheng.crm.settings.service;

import cn.cheng.crm.exception.LoginException;
import cn.cheng.crm.settings.domain.User;

import java.util.List;

public interface UserService {

    User login(String loginAct, String loginPwd, String ip) throws LoginException;

    List<User> getUserList();
}
