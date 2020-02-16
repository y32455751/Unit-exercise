package com.mypro.www;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoggerUtil {
    private final Logger logger = LoggerFactory.getLogger(new Exception().getStackTrace()[1].getClassName());

    private String className = new Exception().getStackTrace()[1].getClassName();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");//设置日期格式


    public LoggerUtil(){

    }

    public LoggerUtil(String className){
        this.className = className;
    }

    public void info(String content){
        this.info("","",0,content,null);
    }

    public void err(String content){
        this.err("","",0,content,null);
    }

    public void info(Object... param){
        this.info("","",0,"",param);
    }

    public void err(Object... param){
        this.err("","",0,"",param);
    }

    public void info(String path,String content,Object... param){
        logger.info(getContent(path,"",0,content,param).toString());
    }

    public void err(String path,String content,Object... param){
        logger.warn(getContent(path,"",0,content,param).toString());
    }



    public void info(HttpServletRequest request, String content){
        int user_id = 0;
        String addr = "";
        String path = "";
        if(null != request){
            user_id = request.getIntHeader("API-MEMBER-ID");
            addr = this.getIpAddr(request);
            path = request.getServletPath();
        }
        this.info(path,addr,user_id,content,null);
    }

    public void err(HttpServletRequest request, String content){
        int user_id = 0;
        String addr = "";
        String path = "";
        if(null != request){
            user_id = request.getIntHeader("API-MEMBER-ID");
            addr = this.getIpAddr(request);
            path = request.getServletPath();
        }
        this.err(path,addr,user_id,content,null);
    }

    public void err(HttpServletRequest request, String content, Object... param){
        int user_id = 0;
        String addr = "";
        String path = "";
        if(null != request){
            user_id = request.getIntHeader("API-MEMBER-ID");
            addr = this.getIpAddr(request);
            path = request.getServletPath();
        }
        this.err(path,addr,user_id,content,param);
    }

    public void info(HttpServletRequest request, Object... param){
        int user_id = 0;
        String addr = "";
        String path = "";
        if(null != request){
            user_id = request.getIntHeader("API-MEMBER-ID");
            addr = this.getIpAddr(request);
            path = request.getServletPath();
        }
        this.info(path,addr,user_id,"",param);
    }

    public void info(HttpServletRequest request, String content, Object... param){
        int user_id = 0;
        String addr = "";
        String path = "";
        if(null != request){
            user_id = request.getIntHeader("API-MEMBER-ID");
            addr = this.getIpAddr(request);
            path = request.getServletPath();
        }
        this.info(path,addr,user_id,content,param);
    }

    public void err(HttpServletRequest request, Object... param){
        int user_id = 0;
        String addr = "";
        String path = "";
        if(null != request){
            user_id = request.getIntHeader("API-MEMBER-ID");
            addr = this.getIpAddr(request);
            path = request.getServletPath();
        }
        this.err(path,addr,user_id,"",param);
    }

    public void info(String path,String addr,int user_id,String content,Object... param){
        logger.info(getContent(path,addr,user_id,content,param).toString());
    }

    public void err(String path,String addr,int user_id,String content,Object... param){
        logger.warn(getContent(path,addr,user_id,content,param).toString());
    }

    private StringBuffer getContent(String path,String addr,int user_id,String content,Object... param){
        StringBuffer info = new StringBuffer();
//        info.append(df.format(new Date()));
//        info.append(" \033[36;4m" + className + "\033[0m : ");
//        if(type == 0)
//            info.append("  -  \033[32;4m"+"DEBUG"+"\033[0m : "+content);
//        else
//            info.append("  -  \033[32;4m"+"ERROR"+"\033[0m : "+content);
        if(user_id > 0){
            info.append(" \033[32;4m"+user_id+"\033[0m");
        }
        if(StringUtils.isNotEmpty(addr)){
            info.append(" \033[35;4m"+ addr + "\033[0m ");
        }
        info.append(path + " ");
        info.append(content);
        if(null != param && param.length > 0){
            info.append(" 参数:");
            for(Object obj : param){
                info.append(obj+",");
            }
        }
        return info;
    }
    /**
     * 获取本机的外网ip地址
     * @return
     */
    public String getV4IP(){
        String ip = "";
        String chinaz = "http://ip.chinaz.com";

        StringBuilder inputLine = new StringBuilder();
        String read = "";
        URL url = null;
        HttpURLConnection urlConnection = null;
        BufferedReader in = null;
        try {
            url = new URL(chinaz);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedReader( new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
            while((read=in.readLine())!=null){
                inputLine.append(read+"\r\n");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Pattern p = Pattern.compile("\\<dd class\\=\"fz24\">(.*?)\\<\\/dd>");
        Matcher m = p.matcher(inputLine.toString());
        if(m.find()){
            String ipstr = m.group(1);
            ip = ipstr;
        }
        return ip;
    }

    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            InetAddress inet = null;
            try {
                inet = InetAddress.getLocalHost();
                ip = inet.getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return ip;
    }

    /**
     * 获取Ip地址
     * @param request
     * @return
     */
    public String getIpAdrress(HttpServletRequest request) {
        String Xip = request.getHeader("X-Real-IP");
        String XFor = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = XFor.indexOf(",");
            if(index != -1){
                return XFor.substring(0,index);
            }else{
                return XFor;
            }
        }
        XFor = Xip;
        if(StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
            return XFor;
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getRemoteAddr();
        }
        return XFor;
    }


}
