package com.qcode365.project.core.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
 
//适配表格宽度
public class ExcelUtil {
	public static void setSizeColumn(XSSFSheet sheet, int size) {
		for (int columnNum = 0; columnNum < size; columnNum++) {
			int columnWidth = sheet.getColumnWidth(columnNum) / 256;
			for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
				XSSFRow currentRow;
				// 当前行未被使用过
				if (sheet.getRow(rowNum) == null) {
					currentRow = sheet.createRow(rowNum);
				} else {
					currentRow = sheet.getRow(rowNum);
				}
				if (currentRow.getCell(columnNum) != null) {
					XSSFCell currentCell = currentRow.getCell(columnNum);
					if (currentCell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
						int length = currentCell.getStringCellValue().getBytes().length;
						if (columnWidth < length)
							columnWidth = length;
					}
				}
			}
			columnWidth = columnWidth * 256;
			sheet.setColumnWidth(columnNum, columnWidth >= 65280 ? 6000 : columnWidth);
		}
	}
	
	// 批量导入
	public static Map<String, Object> importFromExcel(HttpServletRequest request,String fileid) throws Exception {
		 Map<String, Object> respMap =new HashMap<String, Object>();
	       try {
 
	           MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
	           MultipartFile file = multipartRequest.getFile(fileid);//获取文件流\
	          
	           //把excel中内容遍历到list
	           XSSFWorkbook book = new XSSFWorkbook(file.getInputStream());
	           XSSFSheet  sheet = book.getSheetAt(0);
	           
	 
	           //-获取表头---------------------------------
	           XSSFRow rowTitle = sheet.getRow(0);//保证第一行为表头
	           int firstCell = rowTitle.getFirstCellNum();
	           int lastCell = rowTitle.getLastCellNum();
	           List<String> FieldsList=new ArrayList<String>();
	           for(int j=firstCell;j<lastCell;j++){
	               XSSFCell cell = rowTitle.getCell(j);
	               String fieldname=cell.getStringCellValue();
	               fieldname=fieldname.replace("【", "[");
	               if ("[".indexOf(fieldname)>-1)
	               {
	            	   String[] f=fieldname.split("\\[");
	            	   fieldname=f[0];  
	               }
	               FieldsList.add(fieldname.trim());
	           }

	           //读取Excel内容 循环所有行---------------
	           List<Map<String,Object>>ValuesList=new ArrayList<Map<String,Object>>();
	           int firstRowIndex = sheet.getFirstRowNum()+1;//获取第一行
	           int lastRow = sheet.getLastRowNum();//获取最后一行
	           for(int i=firstRowIndex;i<=lastRow;i++){
	               XSSFRow row = sheet.getRow(i);
	               //行不为空时取列
	               if(row==null ){
	                   continue;
	               }
	   
	               //根据列数循环取数据
	               Map<String,Object> map=new HashMap();
	               for(int j=firstCell;j<lastCell;j++){
	                   //但前列格子对象
	                   XSSFCell  cell = row.getCell(j);
	                   //格子不为null时继续取值
	                   if(cell==null) {
	                       continue;
	                   }

	                   //类型转换
	                   if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
	                       cell.setCellType(XSSFCell.CELL_TYPE_STRING);
	                   }
	                   //赋值给List
	                   String val = cell.getStringCellValue();
	                   map.put(FieldsList.get(j), val);
	               }
	               ValuesList.add(map);
	           }
	           respMap.put("FieldsList", FieldsList);
	           respMap.put("ValuesList", ValuesList);
	           respMap.put("statusCode", 200);
	       } catch (Exception e) {
	    	   e.printStackTrace();
	    	   respMap.put("statusCode", 400);
	       }
	       
	       return respMap;
	   
	}
	 //批量导出
   public static void exportToExcel(String filename,String title,Map<String, Object> respMap,String[] alias,String[] aliasV, HttpServletResponse response){
	 	  response.setContentType("octets/stream");
	 	  try {
	 	     response.addHeader("Content-Disposition", "attachment;filename="+new String(filename.getBytes("gb2312"), "ISO8859-1" )+".xls");
	 	     OutputStream out = response.getOutputStream();	      
	 	     ByteArrayOutputStream byteArrayOut = null;
	 		 XSSFWorkbook workbook = null;
	 		 BufferedImage bufferImg = null;
	 		 
	 		 try { 			
	 			
	 			List<Map<String, Object>> list =(List<Map<String, Object>>)respMap.get("rows");
	 			
	 			workbook = new XSSFWorkbook();
	 			XSSFSheet sheet = workbook.createSheet();
	 			workbook.setSheetName(0, title);
	 			XSSFRow row = sheet.createRow(0);
	 			sheet.setColumnWidth(0, 2048);
	 			
	 			XSSFCell cell;
	 			XSSFCellStyle cellStyle = workbook.createCellStyle();
	 			XSSFFont font = workbook.createFont();
	 			font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
	 			cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
	 			cellStyle.setFont(font);
	 			// 创建标题
	 			for (int i = 0; i < alias.length; i++) {
	 				cell = row.createCell(i);
	 				cell.setCellValue(alias[i]);
	 				cell.setCellStyle(cellStyle);
	 			}
	 			// 数据遍历显示
	 			for (int i = 0; i < list.size(); i++) {
	 				row = sheet.createRow(i+1);
	 				for (int j = 0; j < alias.length; j++) {
	 					cell = row.createCell(j);
	 					if(list.get(i).get(aliasV[j])!=null) {
	 						cell.setCellValue(list.get(i).get(aliasV[j]).toString());  
	 					}
	 				}
	 			}
	 			for (int i = 1; i < alias.length; i++) {
	 				sheet.autoSizeColumn(i);
	 				setSizeColumn(sheet, alias.length);
	 			}
	 			workbook.write(out);
	 			out.flush();

	 		} catch (Exception io) { 			
	 		} finally {
	 			try {
	 				if(byteArrayOut != null){byteArrayOut.close();}
	 				if(workbook != null){workbook=null;}
	 				if(out != null){out.close();}
	 			} catch (IOException e) {
	 				e.printStackTrace();
	 			}
	 		}
	      
	 	} catch (Exception e) {
	 	  e.printStackTrace();
	 	}
	  }
	
	
}
