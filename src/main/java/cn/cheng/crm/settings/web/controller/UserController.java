package cn.cheng.crm.settings.web.controller;

import cn.cheng.crm.settings.domain.User;
import cn.cheng.crm.settings.service.Impl.UserServiceImpl;
import cn.cheng.crm.settings.service.UserService;
import cn.cheng.crm.utils.MD5Util;
import cn.cheng.crm.utils.PrintJson;
import cn.cheng.crm.utils.ServiceFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("进入用户控制器");
        String path = request.getServletPath();

        if ("/settings/user/login.do".equals(path)) {
            login(request, response);
        } else if ("/settings/user/login.do".equals(path)) {
            //login(request, response);
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("进入到验证登录操作");

        String loginAct = request.getParameter("loginAct");
        String loginPwd = request.getParameter("loginPwd");

        //将密码的明文形式转换为MD5的密文形式
        loginPwd = MD5Util.getMD5(loginPwd);

        //接受IP地址
        String ip = request.getRemoteAddr();
        System.out.println("ip----> " + ip);

        //未来业务开发，同意使用代理类型接口对象
        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());

        try{
            User user = userService.login(loginAct, loginPwd, ip);
            request.getSession().setAttribute("user", user);
            //如果程序执行到此处，说明业务层没有为controller抛出任何异常表示登录成功
            //为前端返回
            PrintJson.printJsonFlag(response,true);

        }catch (Exception e){
            e.printStackTrace();
            //程序执行了catch语句块，说明业务层为我们验证登录失败，为controller抛出了异常
            //表示登录失败
            //为前端返回，从异常Exception e中取信息
            String msg = e.getMessage();
            /*我们现在作为controller，需要为ajax请求提供多项信息
            * 可以有两种方式来处理：
            * （1）将多项错误信息打包为map,将map解析为json串
            * （2）创建一个vo
            *       private boolean success;
            *       private String msg;
            *   如果将来还会大量使用我们可以创建一个vo类，方便使用
            *   如果对于展现的信息只有在这个需求中使用那我们使用map就可以了，免得多余创建VO类*/
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("success",false);
            map.put("msg",msg);
            PrintJson.printJsonObj(response,map);
        }
    }
}
