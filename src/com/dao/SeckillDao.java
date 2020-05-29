package com.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.entity.Seckill;

/**
 * @author wenbochang
 * @date 2018��1��12��
 */

public interface SeckillDao {

	/**
	 * ����һ�������Ĳ���
	 * 
	 * ����������ɱ�ɹ��ˣ���ôӦ�ý�����е�number��������һ��
	 * ���Ҳ�����ɱ�ɹ��ļ�¼��SuccessKilled
	 * 
	 * ����һ�������������ɹ����򷵻� > 1 ���� ���� 0
	 */
	int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);
	
	/**
	 * ����Ʒ��Id����ѯ��Ʒ��ϸ
	 */
	Seckill queryById(long seckillId);

	/**
	 * ��ѯ���м�¼��
	 * ����ƫ������ѯ��ɱ��Ʒ���б�
	 */
	List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);
	//List<Seckill> queryAll(Map<String, Object> map);
	
	/**
	 * ʹ�ô洢����ִ����ɱ
	 * 
	 * @param paramMap
	 */
	void killByProcedure(Map<String, Object> paramMap);
}






















