package cn.cheng.crm.workbench.service;

import cn.cheng.crm.workbench.domain.Activity;
import cn.cheng.crm.workbench.domain.Clue;
import cn.cheng.crm.workbench.domain.Tran;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface ClueService {

    boolean save(Clue clue);

    Clue detail(String id);

    boolean unbund(String id);

    boolean bund(String cid, String[] aids);

    boolean convert(String clueId, Tran t, String createBy);
}
