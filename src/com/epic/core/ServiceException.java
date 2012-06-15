package com.epic.core;

/**
 * server 的异常
 * @author joe
 * @date  2011-10-24 下午03:33:11
 */
public class ServiceException extends Exception implements MessageAlertable{
	private static final long serialVersionUID = 1L;
   
	public ServiceException(String msg,Throwable e){
		super(msg, e);
	}
	
}
