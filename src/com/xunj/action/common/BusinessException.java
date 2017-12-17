package com.xunj.action.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BusinessException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 894942023208221662L;
	private final Log log = LogFactory.getLog(getClass());
	 public BusinessException() throws Exception
	 {
	   log.error(super.getMessage(),this);
	   throw new Exception(super.getMessage());
	 }
   public BusinessException(String message) throws Exception
     {
	   log.error(message,this);
	   throw new Exception(message,this);
     }
   public BusinessException(String message,Throwable cause){
	   super(message,cause);
	   log.error(message,this);
	 }

	   /**

	   * 只有异常抛出

	   * @param cause

	   */

	   public BusinessException(Throwable cause){
	   super(cause);
	   log.error(cause.getMessage(),this);
	   }
}
