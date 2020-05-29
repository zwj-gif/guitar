package com.service;

import java.util.List;

import com.dto.Exposer;
import com.dto.SeckillExecution;
import com.entity.Seckill;
import com.exception.RepeatKillException;
import com.exception.SeckillCloseException;
import com.exception.SeckillException;

/**
 * ҵ���߼���ӿ�
 * 
 * @author wenbochang
 * @date 2018��1��12��
 */

public interface SeckillService {
	
	/**
	 * ��ѯ������ɱ��Ʒ�ļ�¼
	 * @return
	 */
	List<Seckill> getSeckillList();
	
	/**
	 * ��ѯ������ɱ��¼
	 * @param seckillId
	 * @return
	 */
	Seckill getById(long seckillId);
	
	/**
	 * ����ɱʱ��û�е���ʱ�����ϵͳʱ�����ɱʱ��
	 * ���������ɱ�Ľӿڣ��������û����Ե����ɱ�İ�ť
	 * 
	 * @param seckillId
	 */
	Exposer exportSeckillUrl(long seckillId);

	/**
	 * ִ����ɱ
	 * md5�Ƿ�ֹ�û� ƴ��url��ַ
	 * 
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 */
	SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
		throws SeckillException, RepeatKillException, SeckillCloseException;
	
	/**
	 * �洢����
	 * 
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 * @return
	 * @throws SeckillException
	 * @throws RepeatKillException
	 * @throws SeckillCloseException
	 */
	SeckillExecution executeSeckillProduce(
			long seckillId, long userPhone, String md5);
}














