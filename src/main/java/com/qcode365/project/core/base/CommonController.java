package com.qcode365.project.core.base;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.qcode365.project.core.util.CreateSecurityCodeANDImage;

@Controller
public class CommonController {
       
   private Logger log =  LoggerFactory.getLogger(this.getClass());
   
   // 生成验证码并保存到session
   @RequestMapping(value = "getimage")
   public void getImage(HttpServletResponse response, HttpServletRequest request) {
       FileInputStream fis = null;
       response.setContentType("image/gif");
       try {
           // 通过response获得输出流
           OutputStream out = response.getOutputStream();
           
           // 获取到图片验证码，这个静态类需要自己写
           String securityCodeString = CreateSecurityCodeANDImage.getSecurityCode();
           log.info("==========" + securityCodeString);
           
           request.getSession().setAttribute("securityCode", securityCodeString);
           ByteArrayInputStream image = CreateSecurityCodeANDImage.getImageAsInputStream(securityCodeString);
           byte[] b = new byte[image.available()];
           image.read(b);
           out.write(b);
           out.flush();
       } catch (Exception e) {
           e.printStackTrace();
       } finally {
           if (fis != null) {
               try {
                   fis.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       }
   }
   /*// 生成验证码并保存到session
   @RequestMapping(value = "/error")
   public String errors(HttpServletResponse response, HttpServletRequest request) {
       return "error";
   }*/
}

