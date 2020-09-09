package com.qcode365.project.core.base;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import com.baomidou.mybatisplus.plugins.Page;
import com.qcode365.project.core.Exception.BussinessException;
import com.qcode365.project.core.shiro.ShiroUtil;
import com.qcode365.project.core.util.DateUtil;

//===================版权说明=======================/
//本快速开发框架系统 版权归 快码猿所有                                                                    /
//本框架源代码由快码猿提供                               /
//注册商标：快码猿 QCode                              / 
//备注时间：2019-10-01                              / 
//===============================================/
public class BaseController {

   SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
   SimpleDateFormat tf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

   public ModelAndView mv = this.getModelAndView();
   //////////////////////

   @Autowired
   protected JdbcTemplate jdbcTemplate;
   /*@Autowired
   protected CommService commService;
   */
   
   protected ServletContext application;
   protected HttpServletRequest request;
   protected HttpServletResponse response;

   protected SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
   protected SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   protected Integer pageNum=1;
   protected Integer numPerPage=20;
   protected Integer totalCount;
   protected File img;             //单个文件上传
   protected String imgFileName;
   protected File[] files;         //多个文件上传
   protected String[] filesFileName;
   protected Map<String,Object> responseMap = new HashMap<String, Object>();

   //protected String _basePath="resource/templateproject/";

   //条件查询参数
   protected String condition1;
   protected String condition2;
   protected String condition3;
   protected InputStream excelFile;  //导出报表文件流
   protected String excelFileName;   //导出报表文件名
   
   protected Map<String, Object> buildNewSuccessResultMap() {
       return buildNewResultMap("200","操作成功");
   }
   protected Map<String, Object> buildSuccessResultMap(Page<Map<String,Object>> page, Map<String, Object> respMap) {
       DateUtil.buildList(page.getRecords());
       respMap.put("rows", page.getRecords());
       respMap.put("total", page.getTotal());
       return buildSuccessResultMap(respMap);
   }
   protected Map<String, Object> buildSuccessResultMap(Map<String,Object> map) {
       return buildResultMap(map,"200","操作成功");
   }
   protected Map<String, Object> buildSuccessResultMap(Map<String,Object> map,String msg) {
       return buildResultMap(map,"200",msg);
   }
   protected Map<String, Object> buildFailedResultMap(Map<String,Object> map,BussinessException e) {
       return buildResultMap(map,e.getCode(),e.getMsg());
   }
   protected Map<String, Object> buildNewFailedResultMap(BussinessException e) {
       return buildNewResultMap(e.getCode(),e.getMsg());
   }
   protected Map<String, Object> buildNewFailedResultMap() {
       return buildNewResultMap("400","操作失败");
   }
   protected Map<String, Object> buildFailedResultMap(Map<String,Object> map) {
       return buildResultMap(map,"400","操作失败");
   }
   protected Map<String, Object> buildNewResultMap(String code, String msg) {
       Map<String,Object> map = new HashMap<>();
       map.put("statusCode", code);
       map.put("msg", msg);
       return map;
   }
   protected Map<String, Object> buildResultMap(Map<String,Object> map,String code, String msg) {
       map.put("statusCode", code);
       map.put("msg", msg);
       return map;
   }

   protected void buildErrorPage(ModelAndView mv, String msg) {
       mv.addObject("msg", msg);
       mv.setViewName("systemManagement/error/500");
   }
   /*@ExceptionHandler
   public Map<String,Object> exceptionHandler(Exception e){
       if(e instanceof BussinessException) {
           buildNewFailedResultMap((BussinessException) e);
       }
       //e.printStackTrace();
       log.error(e.toString(), e);
       return buildNewFailedResultMap();
   }*/
   
   
   //protected String base_path="/tools/files/bpams/";//生产上传地址
   //protected String base_path="/tools/apache-tomcat-8.0.53/resources/fixas/";//沙箱上传文件地址（无法在mnt下新建文件夹）
   //protected String base_path="/mnt/resources/fixas/";//测试上传地址

   /*static {
       Properties prop = new Properties();
       InputStream in = BaseController.class.getClassLoader().getResourceAsStream("base.properties");
       try {
           prop.load(in);
           base_path = prop.getProperty("base.base_path").trim();
       } catch (Exception e) {
           System.out.println("读取base.properties文件的base.base_path属性失败");
           e.printStackTrace();
       }
   }*/
   
   public String getExcelFileName() {
       return excelFileName;
   }
   public void setExcelFileName(String excelFileName) {
       this.excelFileName = excelFileName;
   }
   public InputStream getExcelFile() {  
        return excelFile;  
    }  
    public void setExcelFile(InputStream excelFile) {  
        this.excelFile = excelFile;  
    }
 
   
   
   
   
   
   


   
    

   //==============GET SET========================
   public void setServletContext(ServletContext application) {
       this.application = application;
   }

   public void setServletRequest(HttpServletRequest request) {
       this.request = request;
   }

   public void setServletResponse(HttpServletResponse response) {
       this.response = response;
   }

   public Integer getPageNum() {
       return pageNum;
   }

   public void setPageNum(Integer pageNum) {
       this.pageNum = pageNum;
   }

   public Integer getNumPerPage() {
       return numPerPage;
   }

   public void setNumPerPage(Integer numPerPage) {
       this.numPerPage = numPerPage;
   }

   public Integer getTotalCount() {
       return totalCount;
   }

   public void setTotalCount(Integer totalCount) {
       this.totalCount = totalCount;
   }

   public File getImg() {
       return img;
   }

   public void setImg(File img) {
       this.img = img;
   }

   public String getImgFileName() {
       return imgFileName;
   }

   public void setImgFileName(String imgFileName) {
       this.imgFileName = imgFileName;
   }

   public File[] getFiles() {
       return files;
   }

   public void setFiles(File[] files) {
       this.files = files;
   }

   public String[] getFilesFileName() {
       return filesFileName;
   }

   public void setFilesFileName(String[] filesFileName) {
       this.filesFileName = filesFileName;
   }

   public Map<String, Object> getResponseMap() {
       return responseMap;
   }

   public void setResponseMap(Map<String, Object> responseMap) {
       this.responseMap = responseMap;
   }

   public String getCondition1() {
       return condition1;
   }

   public void setCondition1(String condition1) {
       this.condition1 = condition1;
   }

   public String getCondition2() {
       return condition2;
   }

   public void setCondition2(String condition2) {
       this.condition2 = condition2;
   }

   public String getCondition3() {
       return condition3;
   }

   public void setCondition3(String condition3) {
       this.condition3 = condition3;
   }
   
   
   
   
   
   /**
    * 得到ModelAndView
    */
   public ModelAndView getModelAndView() {
       return new ModelAndView();
   }

   /**
    * 得到request对象
    */
   public HttpServletRequest getRequest() {
       HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

       return request;
   }

    

   @SuppressWarnings("rawtypes")
   protected void initSession(HttpServletRequest request){
       Enumeration em = request.getSession().getAttributeNames();
       while(em.hasMoreElements()){
           request.getSession().removeAttribute(em.nextElement().toString());
       }
   }

        //写入错误记录
        public void WriteErrorLog(String errMsg){
            try{
                jdbcTemplate.execute("insert into sys_errorlogs(errMsg,thetime) values('"+ errMsg+"','"+ new Date()+"')");
            }
            catch(Exception e){
                 e.printStackTrace();
            }
        }
        
   }


