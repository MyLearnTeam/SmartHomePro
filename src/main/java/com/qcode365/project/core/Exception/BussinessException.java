package com.qcode365.project.core.Exception;

@SuppressWarnings("serial")
public class BussinessException extends Exception{
	
	private String code;
	private String msg;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public BussinessException(String code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}
	@Override
	public String toString() {
		return "BussinessException [code=" + code + ", msg=" + msg + "]";
	}
	

}
