   package com.qcode365.project.sysbasedata;
   import com.baomidou.mybatisplus.annotations.TableField;
   import com.baomidou.mybatisplus.annotations.TableId;
   import com.baomidou.mybatisplus.annotations.TableName;
   import com.baomidou.mybatisplus.enums.IdType;
   import java.io.Serializable;
   import com.qcode365.project.core.base.BaseEntity;
   import java.util.Date;
   import java.util.List;
   import java.text.SimpleDateFormat;

   /* 模块名称：数据词典
   * Entity Class
   * @author：快码猿(QCode)
   * @time：2020-01-07
   * List<Map<String,Object>> list =new ArrayList<Map<String, Object>>();
   * Map<String,Object>map=new HashMap<String, Object>();
   */

   @TableName("sys_basedata")
   public class SysBasedata extends BaseEntity<SysBasedata>{
       // Fields
       private static final long serialVersionUID = 1L;
       @TableId(type = IdType.AUTO)
       private Integer param_id; //key id
       
       @TableField
       private Integer parent_id; //上级
       
       @TableField
       private String param_token; //数据类型标识
       
       @TableField
       private Integer pvalue; //数据值
       
       @TableField
       private String ptext; //数据中文
       
       @TableField
       private String remark; //说明
       
       @TableField
       private Integer sort; //排序
       
    
       ///////////////////////////////
       /** default constructor */
       public SysBasedata() {
       }
       /** full constructor */
       @Override
       public String toString() {
           return "SysBasedata [param_id=" + param_id + ",parent_id=" + parent_id + ",param_token=" + param_token + ",pvalue=" + pvalue + ",ptext=" + ptext + ",remark=" + remark + ",sort=" + sort + "]";
       }
       ///////////////////////////////
       @Override
       protected Serializable pkVal() {
           return param_id;
       }
       public Integer getParam_id() {
           return param_id;
       }
       public void setParam_id(Integer param_id) {
           this.param_id = param_id;
       }
       public Integer getParent_id() {
           return parent_id;
       }
       public void setParent_id(Integer parent_id) {
           this.parent_id = parent_id;
       }
       public String getParam_token() {
           return param_token;
       }
       public void setParam_token(String param_token) {
           this.param_token = param_token;
       }
       public Integer getPvalue() {
           return pvalue;
       }
       public void setPvalue(Integer pvalue) {
           this.pvalue = pvalue;
       }
       public String getPtext() {
           return ptext;
       }
       public void setPtext(String ptext) {
           this.ptext = ptext;
       }
       public String getRemark() {
           return remark;
       }
       public void setRemark(String remark) {
           this.remark = remark;
       }
       public Integer getSort() {
           return sort;
       }
       public void setSort(Integer sort) {
           this.sort = sort;
       }
   }

