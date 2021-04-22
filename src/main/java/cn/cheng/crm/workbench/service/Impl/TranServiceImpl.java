package cn.cheng.crm.workbench.service.Impl;

import cn.cheng.crm.utils.SqlSessionUtil;
import cn.cheng.crm.workbench.dao.TranDao;
import cn.cheng.crm.workbench.dao.TranHistoryDao;
import cn.cheng.crm.workbench.service.TranService;

public class TranServiceImpl implements TranService {
    private TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    private TranHistoryDao tranHistoryDao   = SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);


}
