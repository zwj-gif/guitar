package com.exception;

/**
 * �ظ���ɱ���쳣���������쳣��
 * 
 * @author wenbochang
 * @date 2018��1��12��
 */

public class RepeatKillException extends SeckillException {

	private static final long serialVersionUID = 1L;

	public RepeatKillException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public RepeatKillException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
}
