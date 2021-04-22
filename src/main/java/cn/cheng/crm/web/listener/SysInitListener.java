package cn.cheng.crm.web.listener;

import cn.cheng.crm.settings.domain.DicValue;
import cn.cheng.crm.settings.service.DicService;
import cn.cheng.crm.settings.service.Impl.DicServiceImpl;
import cn.cheng.crm.utils.ServiceFactory;
import com.fasterxml.jackson.databind.util.ISO8601Utils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;

public class SysInitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        /*改方法是用来监听上下文作用域的方法，当服务器启动，上下文域对象创建对象创建
        完毕后，马上执行该方法

        event：该参数能够取得监听的对象
                监听的是什么对象，就可以通过该参数取得什么对象
                例如我们现在监听的是上下文域对象，通过该参数就可以取得上下文域对象
        * */
        System.out.println("服务器处理缓存字典开始");
        ServletContext application = event.getServletContext();
        DicService ds = (DicService) ServiceFactory.getService(new DicServiceImpl());
        /*7个list
        可以打包成一个map
        * */
        Map<String, List<DicValue>> map =ds.getAll();
        //将map解析为上下文域对象中保存的键值对
        Set<String> set = map.keySet();
        for (String key:set){
            application.setAttribute(key,map.get(key));
        }
        System.out.println("服务器缓存处理数据字典结束");

        //---------------------------------------------------

        //数据字典处理完毕后，处理Stage2Possibility.properties文件
        /*
        * 处理Stage2Possibility.properties文件步骤：
        *   解析该文件，将该属性文件中的键值对关系处理为Java中键值对关系（map）
        *
        *   Map<String(阶段),String(可能性)> pMap = .......
        *   pMap.put("01",10);
        *   pMap.put("02",25);
        *   ...
        *   ...
        *   pMap.put("07",100);
        *
        *   pMap保存值之后，放在服务器缓存中
        *   application。setAttribute("pMap",pMap);
        * */
        //解析properties文件
        Map<String,String> pMap = new HashMap<>();
        ResourceBundle rb = ResourceBundle.getBundle("Stage2Possibility");
        Enumeration<String> e = rb.getKeys();
        while (e.hasMoreElements()){
            //阶段
            String key = e.nextElement();
            //可能性
            String value = rb.getString(key);

            pMap.put(key,value);
        }
        //将pMap保存到服务器缓存中
        application.setAttribute("pMap",pMap);
    }
}
