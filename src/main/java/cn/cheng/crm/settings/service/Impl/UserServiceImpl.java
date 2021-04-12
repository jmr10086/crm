package cn.cheng.crm.settings.service.Impl;

import cn.cheng.crm.exception.LoginException;
import cn.cheng.crm.settings.dao.UserDao;
import cn.cheng.crm.settings.domain.User;
import cn.cheng.crm.settings.service.UserService;
import cn.cheng.crm.utils.DateTimeUtil;
import cn.cheng.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private UserDao dao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public User login(String loginAct, String loginPwd, String ip) throws LoginException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("loginAct", loginAct);
        map.put("loginPwd", loginPwd);
        User user = dao.login(map);
        if (user == null) {
            throw new LoginException("账号密码错误");
        }
        //如果程序能执行到此处，说明账号密码是正确的
        //需要继续验证失效时间
        String expireTime = user.getExpireTime();
        String currentTime = DateTimeUtil.getSysTime();
        //指定的时间小于当前的时间返回负数
        if (expireTime.compareTo(currentTime) < 0) {
            throw new LoginException("账号已失效");
        }
        //判定账号是否是锁定状态
        String lockState = user.getLockState();
        if ("0".equals(lockState)) {
            throw new LoginException("账号已锁定");
        }
        //判断ip地址是否存在权限
        String allowIps = user.getAllowIps();
        if (!allowIps.contains(ip)){
            throw new LoginException("您的ip地址受限");
        }
        return user;
    }
}
