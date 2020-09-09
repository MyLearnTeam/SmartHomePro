package com.qcode365.project.core.util;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

//新补充的公用函数
public class YhwlUtil {
    
           
   public static boolean isValidDate(Object obj) throws java.text.ParseException {
       boolean convertSuccess = true;
       // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       String str = obj == null ? null : obj.toString();
       if("".equals(str)) {
           return false;
       }
       String s = str;
       try {
           // 设置lenient为false.
           // 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
           format.setLenient(false);
           if (str != null) {
               if (str.indexOf(".") > 0) {
                   int index = str.indexOf(".");
                   s = str.substring(0, index);
               }
           }
           format.parse(s);
       } catch (Exception e) {
           // e.printStackTrace();
           // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
           convertSuccess = false;
       }

       /*
        * if (convertSuccess == false){//再检查是否日期格式 SimpleDateFormat format2 = new
        * SimpleDateFormat("yyyy-MM-dd"); String str2 = obj==null?null:obj.toString();
        * String s2=str2; try { // 设置lenient为false. //
        * 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
        * format2.setLenient(false); if (str2!=null){ if(str2.indexOf(".")>0){ int
        * index = str2.indexOf("."); s2= str2.substring(0, index); } }
        * format2.parse(s2); convertSuccess =true; } catch (Exception e) { //
        * e.printStackTrace(); // 如果throw
        * java.text.ParseException或者NullPointerException，就说明格式不对 convertSuccess =
        * false; } }
        */

       return convertSuccess;
   }

   private static boolean isMatch(String regex, String orginal) {
       if (orginal == null || orginal.trim().equals("")) {
           return false;
       }
       Pattern pattern = Pattern.compile(regex);
       Matcher isNum = pattern.matcher(orginal);
       return isNum.matches();
   }

   public static boolean isPositiveInteger(String orginal) {
       if (orginal == null || orginal.trim().equals("")) {
           return false;
       }
       return isMatch("^\\+{0,1}[1-9]\\d*", orginal);
   }

   public static boolean isNegativeInteger(String orginal) {
       if (orginal == null || orginal.trim().equals("")) {
           return false;
       }
       return isMatch("^-[1-9]\\d*", orginal);
   }

   public static boolean isWholeNumber(String orginal) {
       if (orginal == null || orginal.trim().equals("")) {
           return false;
       }
       return isMatch("[+-]{0,1}0", orginal) || isPositiveInteger(orginal) || isNegativeInteger(orginal);
   }

   public static boolean isPositiveDecimal(String orginal) {
       if (orginal == null || orginal.trim().equals("")) {
           return false;
       }
       return isMatch("\\+{0,1}[0]\\.[1-9]*|\\+{0,1}[1-9]\\d*\\.\\d*", orginal);
   }

   public static boolean isNegativeDecimal(String orginal) {
       if (orginal == null || orginal.trim().equals("")) {
           return false;
       }
       return isMatch("^-[0]\\.[1-9]*|^-[1-9]\\d*\\.\\d*", orginal);
   }

   public static boolean isDecimal(String orginal) {
       if (orginal == null || orginal.trim().equals("")) {
           return false;
       }
       return isMatch("[-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+", orginal);
   }

   public static boolean isRealNumber(String orginal) {
       if (orginal == null || orginal.trim().equals("")) {
           return false;
       }
       return isWholeNumber(orginal) || isDecimal(orginal);
   }

      //随机生产数字验证码
    public static int getRandNum(int min, int max) {
        int randNum = min + (int)(Math.random() * ((max - min) + 1));
        return randNum;
    }
    //获取IP地址
    public  static String getIpAddr(HttpServletRequest request) { 
        String ip = request.getHeader("x-forwarded-for"); 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP"); 
        } 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){ 
            ip = request.getHeader("WL-Proxy-Client-IP");
        } 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr(); 
        }
        return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip; 
    }
}

