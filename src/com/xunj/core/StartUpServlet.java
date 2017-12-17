package com.xunj.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.swing.filechooser.FileSystemView;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.ezmorph.object.NumberMorpher;
import net.sf.json.util.JSONUtils;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

//import com.danga.MemCached.MemCachedClient;
//import com.danga.MemCached.SockIOPool;
import com.xunj.action.common.AbstractAction;
import com.xunj.po.CodeDict;
import com.xunj.po.SysFuncInfo;
import com.xunj.po.SysOperationRole;
import com.xunj.util.StateConst;


public class StartUpServlet extends HttpServlet  


{
  private static final long serialVersionUID = 2418806308411687203L;

  public void destroy()
  {
    super.destroy();
  }
//  public static MemCachedClient getInstance(){
//      return new MemCachedClient();
//  }

  public void init()
    throws ServletException
  {
    System.out.println("********************应用程序加载中********************");
    ServletContext application = getServletContext();

    application.setAttribute("globeUsers", new HashMap());

    WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(application);
    CoreDao dao = (CoreDao)wac.getBean("dao");

    System.out.println("******************开始加载系统配置文件********************");
    Properties config = new Properties();
    try {
      String filePath = getServletContext().getRealPath("WEB-INF/classes/system.properties");
      if (filePath.indexOf("%20") != -1)
        filePath = filePath.replaceAll("%20", " ");
      config.load(new FileInputStream(filePath));
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    HashMap map = new HashMap();
    map.put("EachPageSize", (String)config.get("EachPageSize"));
    map.put("uploadFileSavePath", (String)config.get("uploadFileSavePath"));
    map.put("ftpServerIP", (String)config.get("ftpServerIP"));
    map.put("ftpServerPort", (String)config.get("ftpServerPort"));
    map.put("ftpAdminUser", (String)config.get("ftpAdminUser"));
    map.put("ftpAdminPassword", (String)config.get("ftpAdminPassword"));
    map.put("ftpUser", (String)config.get("ftpUser"));
    map.put("ftpPassword", (String)config.get("ftpPassword"));
    map.put("nativeUpLoadPath", (String)config.get("nativeUpLoadPath"));    
    map.put("applicationName", (String)config.get("applicationName"));
    map.put("AutoUrl1", (String)config.get("AutoUrl1"));
//    map.put("LogSwitch", (String)config.get("LogSwitch"));
//    map.put("completeMenberId", (String)config.get("completeMenberId"));
//    map.put("planId", (String)config.get("planId"));
//    map.put("sectionMasterId", (String)config.get("sectionMasterId"));
//    map.put("pageSizeOption", (String)config.get("pageSizeOption"));
//    map.put("classificationCodeSize", (String)config.get("classificationCodeSize"));
//    map.put("customerNumberSize", (String)config.get("customerNumberSize"));
//    map.put("memberNumberSize", (String)config.get("memberNumberSize"));
//    map.put("salePeriodRate", (String)config.get("salePeriodRate"));
//    map.put("saleDays", (String)config.get("saleDays"));
//    map.put("handlerNumberSize", (String)config.get("handlerNumberSize"));   
    getServletContext().setAttribute("sysConfig", map);
    System.out.println("*******************配置文件加载完毕****************************");
    System.out.println("当前应用配置名称：" + ((String)map.get("applicationName")));
    System.out.println("********************注册json转换类型**************************");

    JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] { "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd" }));
    JSONUtils.getMorpherRegistry().registerMorpher(new NumberMorpher(Integer.class));
    JSONUtils.getMorpherRegistry().registerMorpher(new NumberMorpher(Long.class));
    JSONUtils.getMorpherRegistry().registerMorpher(new NumberMorpher(Double.class));

    System.out.println("*******************开始加载字典码数据****************************");
    try {
    	List result = dao.findAll("from CodeDict where state='"+StateConst.STATE_USE+"' and  kindId<>'017' order by codeId ");

		List result2 = dao.findAll("from CodeDict where state='"+StateConst.STATE_USE+"' and  kindId='017' order by codeName ");
		result.addAll(result2);
		this.getServletContext().setAttribute("CodeDict",result);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println("*******************开始加载菜单数据****************************");
    
    String[] servers = {"127.0.0.1:11211"};
    //创建一个连接池
//    SockIOPool pool = SockIOPool.getInstance();
    //设置缓存服务器
//    pool.setServers(servers);
//    //设置初始化连接数，最小连接数，最大连接数以及最大处理时间
//    pool.setInitConn(50);
//    pool.setMinConn(50);
//    pool.setMaxConn(500);
//    pool.setMaxIdle(1000 * 60 * 60);
//    //设置主线程睡眠时间，每3秒苏醒一次，维持连接池大小
////maintSleep 千万不要设置成30，访问量一大就出问题，单位是毫秒，推荐30000毫秒。
//    pool.setMaintSleep(3000);
//    //关闭套接字缓存
//    pool.setNagle(false);
//    //连接建立后的超时时间
//    pool.setSocketTO(3000);
//    //连接建立时的超时时间
//    pool.setSocketConnectTO(0);
//    //初始化连接池
//    pool.initialize();
    
    
//    MemCachedClient cache = StartUpServlet.getInstance();
//    System.out.println("**************************************"+cache);
//    getServletContext().setAttribute("cache", cache);
//    List cacheName=new ArrayList();
//    getServletContext().setAttribute("cacheName", cacheName);
//    Map <String, Map<String, String>> stats = cache.stats();   
//    for (Map.Entry<String, Map<String, String>> m : stats.entrySet()) {   
//        System.out.println(m.getKey());   
//        Map<String, String> values=m.getValue();   
//         for (Map.Entry<String, String> v : values.entrySet()) {   
//              System.out.print(v.getKey()+":");   
//              System.out.println(v.getValue());   
//          }   
//      }  
    
    
    
    try {
      SysFuncInfo po = new SysFuncInfo();
      po.setState("A");
      List FuncInfoList=dao.findByExample(po);
      getServletContext().setAttribute("FuncInfo", FuncInfoList);
      String rootPath="";
      StringBuilder funcInfoData = new StringBuilder();
      funcInfoData.append("[");
      int h=0;
      FuncInfoList = dao.findAll("from SysFuncInfo where state='A' order by funcId ");
      for(int i=0;i<FuncInfoList.size();i++){
    	  SysFuncInfo sysFuncInfo=(SysFuncInfo)FuncInfoList.get(i);
    	  if("1".equals(sysFuncInfo.getFuncType()) && "0".equals(sysFuncInfo.getFuncParentId())){
    		  if(h != 0){
    			  funcInfoData.append(","); 
    		  }
    		  funcInfoData.append("{ text:'"+sysFuncInfo.getFuncName()+"',isexpand:false,children:[ ");
    		  for(int j=0;j<FuncInfoList.size();j++){
    			  SysFuncInfo sysFuncInfo1=(SysFuncInfo)FuncInfoList.get(j);
    			  if("2".equals(sysFuncInfo1.getFuncType()) && sysFuncInfo1.getFuncParentId().equals(sysFuncInfo.getFuncId())){
    				  int k=0;
    				  for(int g=0;g<FuncInfoList.size();g++){
    	    			  SysFuncInfo sysFuncInfo2=(SysFuncInfo)FuncInfoList.get(g);
    					  if("3".equals(sysFuncInfo2.getFuncType())&& sysFuncInfo2.getFuncParentId().equals(sysFuncInfo1.getFuncId())){
    						  if(k != 0){
    	    					  funcInfoData.append(","); 
    	    				  }
    	    				  funcInfoData.append("{ url:\""+rootPath+""+sysFuncInfo2.getUrl()+"\",text:\""+sysFuncInfo2.getFuncName()+"\"}");
    	    				  k++;  						  
    					  } 
    				  }
    			  }
    		  }
    		  funcInfoData.append("]}");
    		  h++;
    	  }
      }
     funcInfoData.append("]");
     getServletContext().setAttribute("funcInfoData", funcInfoData.toString());
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println("*******************启动自动任务线程****************************");
   

    System.out.println("***********************应用启动完成****************************");
  }

  
}