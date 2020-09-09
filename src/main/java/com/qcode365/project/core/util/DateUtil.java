package com.qcode365.project.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {
   
   private static Logger log =  LoggerFactory.getLogger(DateUtil.class);
   
   private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");

   private final static SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");

   private final static SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");

   private final static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   public final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
   public final static SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

   /**
    * 处理list里的data类型值
    * @return 
    */
   public static void buildList(List<Map<String,Object>> list) {
       if(null == list || list.size()==0) {
           return;
       }
       for(Map<String,Object> map:list) {
           for(Entry<String, Object> entry:map.entrySet()) {
               if(entry.getValue() instanceof Date) {
                   String value = entry.getValue().toString();
                   if(value.contains(".")) {
                       value = value.substring(0, value.indexOf("."));
                   }
                   entry.setValue(value);
               }
           }
       }
   }
   
   /**
    * 获取YYYY格式
    * 
    * @return
    */
   public static String getYear() {
       return sdfYear.format(new Date());
   }

   /**
    * 获取YYYY-MM-DD格式
    * 
    * @return
    */
   public static String getDay() {
       return sdfDay.format(new Date());
   }

   /**
    * 获取YYYYMMDD格式
    * 
    * @return
    */
   public static String getDays() {
       return sdfDays.format(new Date());
   }

   /**
    * 获取YYYY-MM-DD HH:mm:ss格式
    * 
    * @return
    */
   public static String getTime() {
       return sdfTime.format(new Date());
   }

   /**
    * @Title: compareDate
    * @Description: TODO(日期比较，如果s>=e 返回true 否则返回false)
    * @param s
    * @param e
    * @return boolean
    * @throws @author
    *             luguosui
    */
   public static boolean compareDate(String s, String e) {
       if (fomatDate(s) == null || fomatDate(e) == null) {
           return false;
       }
       return fomatDate(s).getTime() >= fomatDate(e).getTime();
   }

   /**
    * 格式化日期
    * 
    * @return
    */
   public static Date fomatDate(String date) {
       DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
       try {
           return fmt.parse(date);
       } catch (ParseException e) {
           e.printStackTrace();
           return null;
       }
   }

   /**
    * 校验日期是否合法
    * 
    * @return
    */
   public static boolean isValidDate(String s) {
       DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
       try {
           fmt.parse(s);
           return true;
       } catch (Exception e) {
           // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
           return false;
       }
   }

   public static int getDiffYear(String startTime, String endTime) {
       DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
       try {
           int years = (int) (((fmt.parse(endTime).getTime() - fmt.parse(startTime).getTime()) / (1000 * 60 * 60 * 24)) / 365);
           return years;
       } catch (Exception e) {
           // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
           return 0;
       }
   }

   /**
    * <li>功能描述：时间相减得到天数
    * 
    * @param beginDateStr
    * @param endDateStr
    * @return long
    * @author Administrator
    */
   public static long getDaySub(String beginDateStr, String endDateStr) {
       long day = 0;
       java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
       java.util.Date beginDate = null;
       java.util.Date endDate = null;

       try {
           beginDate = format.parse(beginDateStr);
           endDate = format.parse(endDateStr);
       } catch (ParseException e) {
           e.printStackTrace();
       }
       day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
       // log.debug("相隔的天数="+day);

       return day;
   }

   /**
    * 得到n天之后的日期
    * 
    * @param days
    * @return
    */
   public static String getAfterDayDate(String days) {
       int daysInt = Integer.parseInt(days);

       Calendar canlendar = Calendar.getInstance(); // java.util包
       canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
       Date date = canlendar.getTime();

       SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       String dateStr = sdfd.format(date);

       return dateStr;
   }

   /**
    * 得到n天之后是周几
    * 
    * @param days
    * @return
    */
   public static String getAfterDayWeek(String days) {
       int daysInt = Integer.parseInt(days);

       Calendar canlendar = Calendar.getInstance(); // java.util包
       canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
       Date date = canlendar.getTime();

       SimpleDateFormat sdf = new SimpleDateFormat("E");
       String dateStr = sdf.format(date);

       return dateStr;
   }
   /**
    * 得到n天之后的日期
    *
    * @return
    */
   public static String getCurrentTime() {
       SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       String dateStr = sdfd.format(new Date());

       return dateStr;
   }
   public static void main(String[] args) {
       log.debug(getDays());
       log.debug(getAfterDayWeek("3"));
   }
   //获取本周周一
       protected static Date getNowWeekMonday(Date date) {  
           Calendar cal = Calendar.getInstance();
           cal.setTime(date);
           cal.add(Calendar.DAY_OF_MONTH, -1);
           cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
           return cal.getTime();
       }
       
       //获取本周周日（一周的开始就是周日）
       protected static Date getLastWeekSunday(Date date) {    
           Calendar cal = Calendar.getInstance();
           cal.setTime(date);  
           SimpleDateFormat forday = new SimpleDateFormat("yyyy-MM-dd");
           System.out.println("cal.getTime()=="+forday.format(cal.getTime()));
           cal.add(Calendar.DATE, 7);
           cal.set(Calendar.DAY_OF_WEEK, 1);    
           return cal.getTime();
       }
       //获取下一个旬的日期
       protected static Date getNextXun(Date date) {    
           Calendar cal = Calendar.getInstance();
           cal.setTime(date);  
           Calendar cal2 = Calendar.getInstance();
           cal2.setTime(date);  
           cal2.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
           cal2.add(Calendar.MONTH,1);//月增加1天
           cal2.add(Calendar.DAY_OF_MONTH,-1);
           
           SimpleDateFormat forMM = new SimpleDateFormat("MM");
           SimpleDateFormat fordd = new SimpleDateFormat("dd");
           int lastd = Integer.parseInt(fordd.format(cal2.getTime()));//本月最后一天
           
           int thisM = Integer.parseInt(forMM.format(cal.getTime()));
           cal.add(Calendar.DAY_OF_MONTH, +9);
           int nextM = Integer.parseInt(forMM.format(cal.getTime()));
           int nextd = Integer.parseInt(fordd.format(cal.getTime()));
           if(thisM < nextM){
               cal.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
               //cal.add(Calendar.MONTH,1);//月增加1天
               cal.add(Calendar.DAY_OF_MONTH,-1);
           }
           if(thisM == nextM && nextd<lastd && nextd>20){
               cal.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
               cal.add(Calendar.MONTH,1);//月增加1天
               cal.add(Calendar.DAY_OF_MONTH,-1);
           }
           return cal.getTime();
       }
       
       //获取季度最后一个月的日期
       protected static Date getNextJd(Date date) {    
           Calendar cal = Calendar.getInstance();
           cal.setTime(date);  
           SimpleDateFormat forday = new SimpleDateFormat("yyyy-MM-dd");
           cal.add(Calendar.MONTH, 3);
           cal.add(Calendar.DAY_OF_MONTH,-1);  
           System.out.println("cal.getTime()=="+forday.format(cal.getTime()));
           return cal.getTime();
       }

       //-----------------------------------------------
          
          /**
           * 日期相加减
           * @param time 
           *             时间字符串 yyyy-MM-dd HH:mm:ss
           * @param num
           *             加的数，-num就是减去
           * @return  
           *             减去相应的数量的年的日期
           * @throws ParseException 
           */
          public static Date yearAddNum(Date time, Integer num) {
              //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
              //Date date = format.parse(time);
              
              Calendar calendar = Calendar.getInstance();
              calendar.setTime(time);
              calendar.add(Calendar.YEAR, num);
              Date newTime = calendar.getTime();
              return newTime;
          }
          
          /**
           * 
           * @param time
           *           时间
           * @param num
           *           加的数，-num就是减去
           * @return 
           *          减去相应的数量的月份的日期
           * @throws ParseException Date
           */
          public static Date monthAddNum(Date time, Integer num){
              //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
              //Date date = format.parse(time);
              
              Calendar calendar = Calendar.getInstance();
              calendar.setTime(time);
              calendar.add(Calendar.MONTH, num);
              Date newTime = calendar.getTime();
              return newTime;
          }
          
          /**
           * 
           * @param time
           *           时间
           * @param num
           *           加的数，-num就是减去
           * @return 
           *          减去相应的数量的天的日期
           * @throws ParseException Date
           */
          public static Date dayAddNum(Date time, Integer num){
              //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
              //Date date = format.parse(time);
              
              Calendar calendar = Calendar.getInstance();
              calendar.setTime(time);
              calendar.add(Calendar.DAY_OF_MONTH, num);
              Date newTime = calendar.getTime();
              return newTime;
          }

      /**
       * 获取本月第一天时间
       */
      public static Date getMonthStartDate(){
          Calendar calendar = Calendar.getInstance();
          calendar.set(Calendar.DAY_OF_MONTH,1);
          return calendar.getTime();
      }

      /**
       * 获取本月最后一天
       * 
       */
      public static Date getMonthEndDate(){
          Calendar calendar = Calendar.getInstance();
          calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
          return calendar.getTime();
      }
      /**
       * 获取本周的开始时间
       */
      public static Date getBeginWeekDate(){
          Calendar cal = Calendar.getInstance();
          cal.setTime(new Date());
          int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
          //周日是1 ，周一是 2 ，周二是 3
          //所以，当周的第一天 = 当前日期 - 距离周一过了几天（周一过了0天，周二过了1天，   周日过了6天）
          // 2 - 周一的（dayofweek：2 ）= 0
          // 2 - 周二的（dayofweek：3 ）= -1
          //                      .
          //                      .
          // 2 - 周日的（dayofweek：1） = 1（这个是不符合的需要我们修改）===》2 - 周日的（dayofweek：1 ==》8 ） = -6
          if (dayofweek == 1) {
              dayofweek += 7;
          }
          cal.add(Calendar.DATE, 2 - dayofweek);
          return cal.getTime();
      }

      /**
       * 本周的结束时间
       * 开始时间 + 6天
       */
      public static Date getEndWeekDate(){
          Calendar cal = Calendar.getInstance();
          cal.setTime(new Date());
          int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
          if (dayofweek == 1) {
              dayofweek += 7;
          }
          cal.add(Calendar.DATE, 8 - dayofweek);//2 - dayofweek + 6
          return cal.getTime();
      }
      
   }



