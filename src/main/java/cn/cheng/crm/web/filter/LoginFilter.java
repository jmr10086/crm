package cn.cheng.crm.web.filter;

import cn.cheng.crm.settings.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

        System.out.println("进入到验证有没有登录过的过滤器");

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String path = request.getServletPath();
        //不应该被拦截的资源，放行
        if ("/login.jsp".equals(path) || "/settings/user/login.do".equals(path)){
            chain.doFilter(req,resp);
        }else {
         //其他资源有没有登陆过
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            //如果session域中user不为空，说明登录过
            if (user != null) {
                chain.doFilter(req, resp);
            } else {
                //没有登录过重定向登录页面
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }
        }
    }
}
