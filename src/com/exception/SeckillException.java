package com.exception;

/**
 * 
 * ��������δ���ǵ��쳣������������쳣
 * һ��Ҫ�̳�RunntimeException�����
 * ��Ϊֻ������ʱ�쳣 spring������Ż���лع�
 * 
 * @author wenbochang
 * @date 2018��1��12��
 */

public class SeckillException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SeckillException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public SeckillException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
}
