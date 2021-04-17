package cn.cheng.crm.workbench.web.controller;

import cn.cheng.crm.settings.domain.User;
import cn.cheng.crm.settings.service.Impl.UserServiceImpl;
import cn.cheng.crm.settings.service.UserService;
import cn.cheng.crm.utils.DateTimeUtil;
import cn.cheng.crm.utils.PrintJson;
import cn.cheng.crm.utils.ServiceFactory;
import cn.cheng.crm.utils.UUIDUtil;
import cn.cheng.crm.vo.PaginationVo;
import cn.cheng.crm.workbench.domain.Activity;
import cn.cheng.crm.workbench.domain.ActivityRemark;
import cn.cheng.crm.workbench.service.ActivityService;
import cn.cheng.crm.workbench.service.Impl.ActivityServiceImpl;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("进入市场活动控制器");
        String path = request.getServletPath();

        if ("/workbench/activity/getUserList.do".equals(path)) {
            getUserList(request, response);

        } else if ("/workbench/activity/save.do".equals(path)) {
            save(request, response);

        } else if ("/workbench/activity/pageList.do".equals(path)) {
            pageList(request, response);

        }else if ("/workbench/activity/delete.do".equals(path)) {
            delete(request, response);

        }else if ("/workbench/activity/getUserListAndActivity.do".equals(path)) {
            getUserListAndActivity(request, response);

        }else if ("/workbench/activity/update.do".equals(path)) {
            update(request, response);

        }else if ("/workbench/activity/detail.do".equals(path)) {
            detail(request, response);

        }else if ("/workbench/activity/getRemarkListByAid.do".equals(path)) {
            getRemarkListByAid(request, response);

        }else if ("/workbench/activity/deleteRemark.do".equals(path)) {
            deleteRemark(request, response);

        }else if ("/workbench/activity/saveRemark.do".equals(path)) {
            saveRemark(request, response);

        }else if ("/workbench/activity/updateRemark.do".equals(path)) {
            updateRemark(request, response);

        }
    }

    private void updateRemark(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("执行修改备注的操作");
        String id = request.getParameter("id");
        String noteContent = request.getParameter("noteContent");
        //修改时间为当前的系统时间
        String editTime = DateTimeUtil.getSysTime();
        //修改人为当前登录的用户
        String editBy = ((User) request.getSession().getAttribute("user")).getName();
        String editFlag = "1";

        ActivityRemark ar = new ActivityRemark();
        ar.setEditFlag(editFlag);
        ar.setEditTime(editTime);
        ar.setNoteContent(noteContent);
        ar.setId(id);
        ar.setEditBy(editBy);

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = as.updateRemark(ar);
        Map<String, Object> map = new HashMap<>();
        map.put("success",flag);
        map.put("ar",ar);
        PrintJson.printJsonObj(response,map);
    }

    private void saveRemark(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("执行添加备注操作");

        String noteContent = request.getParameter("noteContent");
        String activityId = request.getParameter("activityId");
        String id =UUIDUtil.getUUID();
        //创建时间为当前的系统时间
        String createTime = DateTimeUtil.getSysTime();
        //创建人为当前登录的用户
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        String editFlag = "0";

        ActivityRemark ar = new ActivityRemark();
        ar.setActivityId(activityId);
        ar.setCreateBy(createBy);
        ar.setCreateTime(createTime);
        ar.setNoteContent(noteContent);
        ar.setId(id);
        ar.setEditFlag(editFlag);
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = as.saveRemark(ar);
        Map<String,Object> map = new HashMap<>();
        map.put("success",flag);
        map.put("ar",ar);
        PrintJson.printJsonObj(response,map);
    }

    private void deleteRemark(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("删除备注操作");
        String id = request.getParameter("id");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = as.deleteRemark(id);
        PrintJson.printJsonFlag(response,flag);
    }

    private void getRemarkListByAid(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("根据市场活动的ID来取得备注信息列表");
        String activityId = request.getParameter("activityId");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<ActivityRemark> arList = as.getRemarkListByAid(activityId);
        PrintJson.printJsonObj(response,arList);

    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("跳转到详细信息页的操作");

        String id = request.getParameter("id");

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Activity a = as.detail(id);
        request.setAttribute("a",a);
        request.getRequestDispatcher("/workbench/activity/detail.jsp").forward(request,response);
    }

    private void update(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("执行市场活动修改操作");

        String id = request.getParameter("id");
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");

        //修改时间为当前的系统时间
        String editTime = DateTimeUtil.getSysTime();
        //修改人为当前登录的用户
        String editBy = ((User) request.getSession().getAttribute("user")).getName();
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        Activity a = new Activity();
        a.setId(id);
        a.setCost(cost);
        a.setName(name);
        a.setOwner(owner);
        a.setEndDate(endDate);
        a.setStartDate(startDate);
        a.setDescription(description);
        a.setCreateTime(editTime);
        a.setCreateBy(editBy);

        boolean flag = as.update(a);
        PrintJson.printJsonFlag(response,flag);
    }

    private void getUserListAndActivity(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("进入到查询用户信息列表和根据市场活动id查询单挑记录的操作");
        String id = request.getParameter("id");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        /*
        总结：
            以后controller调用service的方法，返回值应该是什么
            你得想一想前端要什么，就要从service层取什么

            前端需要的，业务层去要
            uList
            a
            map
            以上两项信息，服用率不高，我们选择使用map打包这两项信息即可

        * */
        Map<String, Object> map = as.getUserListAndActivity(id);
        PrintJson.printJsonObj(response,map);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行市场活动删除操作");

        String ids[] = request.getParameterValues("id");

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = as.delete(ids);
        PrintJson.printJsonFlag(response,flag);
    }

    private void pageList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到查询市场活动信息列表的操作（结合条件查询+分页查询）");

        String name = request.getParameter("name");
        String owner = request.getParameter("owner");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String pageNoStr = request.getParameter("pageNo");
        int pageNo = Integer.valueOf(pageNoStr);
        //每页展现的记录数
        String pageSizeStr = request.getParameter("pageSize");
        //计算出略过的记录数
        int pageSize = Integer.valueOf(pageSizeStr);
        int skipCount = (pageNo-1)*pageSize;
        Map<String, Object> map = new HashMap<>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("starDate",startDate);
        map.put("endDate",endDate);
        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        /*
        前端要：
            市场活动信息列表
            查询的总条数

            业务层拿到以上两项信息之后如果做返回
            map
            vo
            将来的分页查询，每个模块都有，所以我们选择使用一个通用vo，操作起来比较方便

        */

        PaginationVo<Activity> vo = as.pageList(map);
        PrintJson.printJsonObj(response,vo);
    }

    private void save(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("执行市场活动的添加操作");

        String id = UUIDUtil.getUUID();
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");

        //创建时间为当前的系统时间
        String createTime = DateTimeUtil.getSysTime();
        //创建人为当前登录的用户
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        Activity a = new Activity();
        a.setId(id);
        a.setCost(cost);
        a.setName(name);
        a.setOwner(owner);
        a.setEndDate(endDate);
        a.setStartDate(startDate);
        a.setDescription(description);
        a.setCreateTime(createTime);
        a.setCreateBy(createBy);

        boolean flag = as.save(a);
        PrintJson.printJsonFlag(response,flag);
    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("取得用户信息列表");
        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> userList = us.getUserList();
        PrintJson.printJsonObj(response, userList);
    }
}
