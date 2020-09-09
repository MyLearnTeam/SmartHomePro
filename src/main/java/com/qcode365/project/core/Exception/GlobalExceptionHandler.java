package com.qcode365.project.core.Exception;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


/**
 * 
 * @类描述：统一异常处理，包括【普通调用和ajax调用】
 * </br>ControllerAdvice来做controller内部的全局异常处理，但对于未进入controller前的异常，该处理方法是无法进行捕获处理的，SpringBoot提供了ErrorController的处理类来处理所有的异常(TODO)。
 * </br>1.当普通调用时，跳转到自定义的错误页面；2.当ajax调用时，可返回约定的json数据对象，方便页面统一处理。
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	private Logger log =  LoggerFactory.getLogger(this.getClass());
    public static String DEFAULT_ERROR_VIEW = "systemManagement/error/403";

   /* *//**
     * @描述：针对普通请求和ajax异步请求的异常进行处理
     * @throws Exception
     */
    /*@ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object errorHandler(HttpServletRequest request,HttpServletResponse response, Exception e) {
    	e.printStackTrace();
        log.debug(getClass().getName() + ".errorHandler】统一异常处理：request="+request);
        if(isAjax(request)) {
        	Map<String,String> map = new HashMap<>();
        	map.put("statusCode", "400");
        	map.put("msg", "操作失败");
        }
        ModelAndView mv=new ModelAndView();
        mv.addObject("msg", e.getMessage());  	
        mv.setViewName("error");
        
        return mv;
    }*/
    /**
    * 访问某个URL没有权限时抛出UnauthorizedException，
    * @ExceptionHandler({UnauthorizedException.class})注解表示处理：UnauthorizedException 异常，
    * 当异常出现时，返回 refuse 视图。
    */
    @ExceptionHandler({UnauthorizedException.class})
    @ResponseBody
    public Object processUnauthenticatedException(HttpServletRequest request, UnauthorizedException e) {
        log.error("[errorHandler]  您没有权限作此操作 : "+request.getRequestURI());
        //e.printStackTrace();
        if(isAjax(request)) {
        	Map<String,String> map = new HashMap<>();
        	map.put("statusCode", "403");
        	map.put("msg", "您没有权限作此操作!");
        	return map;
        }
        ModelAndView mv=new ModelAndView();
        mv.addObject("msg", e.getMessage()+"</br>"+e.getCause());
        mv.setViewName(DEFAULT_ERROR_VIEW);
        return mv;
    }
    private boolean isAjax(HttpServletRequest request){
        String header = ((HttpServletRequest) request).getHeader("X-Requested-With");
        if("XMLHttpRequest".equalsIgnoreCase(header)){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
