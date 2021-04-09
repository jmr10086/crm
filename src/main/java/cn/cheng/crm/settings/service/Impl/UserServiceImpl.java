package cn.cheng.crm.settings.service.Impl;

import cn.cheng.crm.settings.dao.UserDao;
import cn.cheng.crm.settings.service.UserService;
import cn.cheng.crm.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;

public class UserServiceImpl implements UserService {
    private UserDao dao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

}
