package cn.cheng.crm.web.filter;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse rep, FilterChain chian) throws IOException, ServletException {
        System.out.println("进入到过滤字符的过滤器");
        //过滤post请求中文参数乱码
        req.setCharacterEncoding("utf-8");
        //过滤器响应流响应中文乱码
        rep.setContentType("text/html;charset=utf-8");
        //将请求放行
        chian.doFilter(req,rep);
    }
}
