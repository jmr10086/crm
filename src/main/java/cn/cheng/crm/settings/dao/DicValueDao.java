package cn.cheng.crm.settings.dao;

import cn.cheng.crm.settings.domain.DicValue;

import java.util.List;

public interface DicValueDao {
    List<DicValue> getListByCode(String code);
}
