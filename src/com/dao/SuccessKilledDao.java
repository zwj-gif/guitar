package com.dao;

import org.apache.ibatis.annotations.Param;

import com.entity.SuccessKilled;
import com.entity.SuccessKilledRecord;

/**
 * @author wenbochang
 * @date 2018��1��12��
 */

public interface SuccessKilledDao {
	
	/**
	 * ��ɱ�ɹ���Ȼ�������ɱ�ɹ�������¼
	 * ���Թ����ظ���һ����ֻ����ɱһ��
	 * ����1���ǲ���ɹ�������0���ǲ���ʧ��
	 */
	int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
	
	/**
	 * ����id��ѯSuccessKilled, ��Я����ɱ��Ʒ����ʵ��
	 */
	SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
	
	SuccessKilledRecord queryAllById(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
}








