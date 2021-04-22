package cn.cheng.crm.workbench.service.Impl;

import cn.cheng.crm.utils.SqlSessionUtil;
import cn.cheng.crm.workbench.dao.CustomerDao;
import cn.cheng.crm.workbench.service.CustomerService;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);

    @Override
    public List<String> getCustomerName(String name) {
        List<String> sList = customerDao.getCustomerName(name);
        return sList;
    }
}
