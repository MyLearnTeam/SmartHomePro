package com.qcode365.project.core.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求参数帮助类
 * @author xiongbiao
 *
 */
@SuppressWarnings("all")
public class ComponentHelper {

   
   
   protected static SimpleDateFormat dayDfMhd = new SimpleDateFormat("yyyyMMddHH");
   protected static SimpleDateFormat dayDf = new SimpleDateFormat("yyyyMMdd");
   protected static SimpleDateFormat dayDf1 = new SimpleDateFormat("yyyy-MM-dd");
   protected static SimpleDateFormat dayDf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
   public static String nowDate2(){
       return dayDf2.format(new Date());
   }
   
   public static String nowDate(){
       return dayDfMhd.format(new Date());
   }

   
   
   public static int getStartDateForDayReport() {
       Calendar c = Calendar.getInstance();
       return getStartDateForDayReport(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH) - 31);
   }

   /**
    * get the start date of report, the report is by day base on monthly
    * 
    * @return
    */
   public static int getStartDateForDayReport(int year, int month, int day) {
       Calendar c = Calendar.getInstance();
       c.set(Calendar.YEAR, year);
       c.set(Calendar.MONTH, month);
       c.set(Calendar.DAY_OF_MONTH, day);
       return Integer.parseInt(dayDf.format(c.getTime()));
   }

   /**
    * get end date , the end date is current date by default
    * 
    * @return
    */
   public static int getEndDate() {
       Calendar c = Calendar.getInstance();
       // c.add(Calendar.DAY_OF_MONTH, -1);
       return Integer.parseInt(dayDf.format(c.getTime()));
   }
   
   
   public static Map<String, Object> requestToMap(HttpServletRequest request)
    {
       Enumeration names = request.getParameterNames();
       Map map = new HashMap();
       while (names.hasMoreElements()) {
         String name = (String)names.nextElement();
         
         String[] s = request.getParameterValues(name);
         if (s != null) {
           if (s.length > 1)
           {
               for (int k=0;k<s.length;k++)
               {
                   s[k]=escapeExprSpecialWord(s[k]);
               }
             map.put(name, s);
          }
           else {
             String svalue=escapeExprSpecialWord(s[0]);
             map.put(name, svalue);
           }
         }
       }
       return map;
   }
   //处理转义字符
   public static String escapeExprSpecialWord(String keyword) {  
       if (keyword!=null) {  
           if (isValidDate(keyword)){//是日期格式
               return keyword;  
           }
           //过滤字符
           // String[] fbsArr = { "<", ">","+","CR", "LF","script", "document", "eval", "prompt","xss"};  
           String[] fbsArr = {"script", "document", "eval", "prompt"};  
           for (String key : fbsArr) {  
               if (keyword.contains(key)) {  
                   keyword = keyword.replace(key, "");  
               }  
           }  
          //转义字符
         /*  String[] fbsArr2 = {"|","$", "%", "@", ",", "\"", "<", ">", "(", ")", "+", "CR", "LF", ",", ".", "script", "document", "eval", "prompt"," ","xss"};
          //String[] fbsArr2 = { "|","$", "%","\"","(", ")", "+"};  
           for (String key : fbsArr2) {  
               if (keyword.contains(key)) {  
                   keyword = keyword.replace(key, "\\" + key);  
               }  
           }  */
       }  
       
       return keyword;  
   }   
   public static Map<String, Object> responseMap(String method,boolean isSuccess,int code,String info){
        Map<String, Object> responseMap = new HashMap<String, Object>();
              responseMap.put("method", method);
              responseMap.put("success", isSuccess);
              responseMap.put("code", code);
              responseMap.put("info", info);
      return  responseMap;
   }
   
   //检查是否日期格式
   public static boolean isValidDate(String str) {
       boolean convertSuccess=true;
       // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
       str=str.replace("/", "-");
       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
       // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
           format.setLenient(false);
           format.parse(str);
       } catch (Exception e) {
       // e.printStackTrace();
       // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
           convertSuccess=false;
       } 
       return convertSuccess;
  }
   
}

