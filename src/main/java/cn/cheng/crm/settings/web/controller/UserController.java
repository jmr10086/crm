package cn.cheng.crm.settings.web.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("进入用户控制器");
        String path = request.getServletPath();
        if ("/settings/user/login.do".equals(path)) {
            //login(request, response);
        } else if ("/settings/user/login.do".equals(path)) {
            //login(request, response);
        }
    }
}
