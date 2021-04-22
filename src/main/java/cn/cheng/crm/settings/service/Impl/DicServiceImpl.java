package cn.cheng.crm.settings.service.Impl;

import cn.cheng.crm.settings.dao.DicTypeDao;
import cn.cheng.crm.settings.dao.DicValueDao;
import cn.cheng.crm.settings.domain.DicType;
import cn.cheng.crm.settings.domain.DicValue;
import cn.cheng.crm.settings.service.DicService;
import cn.cheng.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DicServiceImpl implements DicService {
    private DicTypeDao dicTypeDao = SqlSessionUtil.getSqlSession().getMapper(DicTypeDao.class);
    private DicValueDao dicValueDao = SqlSessionUtil.getSqlSession().getMapper(DicValueDao.class);

    @Override
    public Map<String, List<DicValue>> getAll() {

        Map<String, List<DicValue>> map = new HashMap<String, List<DicValue>>();

        //将字典类型列表取出
        List<DicType> dtList = dicTypeDao.getTypeList();
        //将字典类型遍历
        for (DicType dt : dtList){
            //取得每一种类型的字典类型编码
            String code = dt.getCode();
            //根据每一个字典类型来取得字典值列表
            List<DicValue> dvList = dicValueDao.getListByCode(code);
            map.put(code + "List",dvList);
        }
        return map;
    }
}
