package cn.cheng.crm.settings.service;

import cn.cheng.crm.exception.LoginException;
import cn.cheng.crm.settings.domain.User;

public interface UserService {

    User login(String loginAct, String loginPwd, String ip) throws LoginException;

}
