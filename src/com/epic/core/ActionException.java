package com.epic.core;

/**
 * controller 的异常
 * @author joe
 * @date  2011-10-24 下午03:32:48
 */
public class ActionException extends Exception implements MessageAlertable{
	private static final long serialVersionUID = 1L;
   
	public ActionException(String msg,Throwable e){
		super(msg, e);
	}
	public ActionException(String msg){
		super(msg);
	}
}
