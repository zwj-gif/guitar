package com.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.dao.SeckillDao;
import com.dao.SuccessKilledDao;
import com.dao.cache.RedisDao;
import com.dto.Exposer;
import com.dto.SeckillExecution;
import com.entity.Seckill;
import com.entity.SuccessKilled;
import com.enums.SeckillStatEnum;
import com.exception.RepeatKillException;
import com.exception.SeckillCloseException;
import com.exception.SeckillException;

/**
 * ҵ���߼���ľ���ʵ��
 * 
 * @author wenbochang
 * @date 2018��1��13��
 */

@Service
public class SeckillServiceImpl implements SeckillService {

	//ע��service����
	@Autowired
	private SeckillDao seckillDao;
	@Autowired
	private SuccessKilledDao successKilledDao;
	@Autowired
	private RedisDao redisDao;

	// ���ڻ���md5
	private final String salt = "cwb1^^^2^^^3zyj";

	@Override
	public List<Seckill> getSeckillList() {
		return seckillDao.queryAll(0, 4);
	}

	@Override
	public Seckill getById(long seckillId) {
		return seckillDao.queryById(seckillId);
	}

	/**
	 * ����ɱʱ��û�е���ʱ�����ϵͳʱ�����ɱʱ�� ���������ɱ�Ľӿڣ��������û����Ե����ɱ�İ�ť
	 * 
	 * �ж����������ͣ���һ���Ǹ�id��Ʒ�����ڵ������ֱ�ӷ���false �ڶ����ǲ���ʱ�䷶Χ�ڣ�û�е��￪ʼʱ�䣬�����Ѿ������ˡ�
	 * ��������Ϊ��������ô��֤md5����ֹ���ݱ����˴۸ĺ�ƴ�ӡ�
	 * 
	 * @param seckillId
	 */
	@Override
	public Exposer exportSeckillUrl(long seckillId) {

		/**
		 * 2018��1��15��14:59:47
		 * �����Ż�1 ������Ʒ�ŵ�redis������ȥ
		 *  
		 * get from cache
		 * if null
		 * 	  get db
		 *	  put cache
		 */
		Seckill seckill = redisDao.getSeckill(seckillId);
		if (seckill == null) {
			System.out.println("111");
			seckill = seckillDao.queryById(seckillId);
			if (seckill == null) {
				return new Exposer(false, seckillId);
			} else {
				redisDao.putSeckill(seckill);
			}
		}

		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		Date nowTime = new Date();
		if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
			return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
		}

		// ת���ض��ַ����ı���, ������
		String md5 = getMd5(seckillId);
		return new Exposer(true, md5, seckillId);
	}

	// ����md5�㷨������һ��spring�Ĺ����� DigestUtils.md5DigestAsHex(������Ҫbytes����)
	private String getMd5(long seckillId) {
		String base = salt + "/" + seckillId + "/" + salt;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}
	
	/**
	 * ע�⣺  �����ص� ������������������������
	 * spring������ֻ���������ڳ����쳣�Ż���лع�
	 * �����try catch�����˲���spring��Ϊ�ⲻ���쳣Ҳ�Ͳ�����лع���
	 * 
	 * ���Ա�����catch���� ���Ե�throw new SeckillException("seckill data rewrite");
	 * �쳣��spring�Ż���лع�
	 * 
	 * ʹ��ע���������񷽷����ŵ㣺
	 * 1�������Ŷ���ȷ��ע���񷽷���̵ķ�����������һ����֪������һ������
	 * 2����֤���񷽷�ִ��ʱ�価���ܵĶ�
	 * 3���������еķ�������Ҫ����
	 */

	/**
	 * ִ����ɱ md5�Ƿ�ֹ�û� ƴ��url��ַ
	 * 
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 */
	@Transactional
	@Override
	public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
			throws SeckillException, RepeatKillException, SeckillCloseException {

		// ��֤md5
		if (md5 == null || !md5.equals(getMd5(seckillId))) {
			throw new SeckillException("seckill data rewrite");
		}
		/**
		 * ִ����ɱ�߼� 
		 * 1. ���ȼ��ٿ�� 
		 * 2. ��¼�������Ϊ
		 */
		try {
			Date nowTime = new Date();
			/**
			 * ����ΪʲôҪ��sql������жϿ�������߲������أ�
			 * ����һ�����Ȳ�ѯ��Ʒ������������ > 1 Ȼ���ȥ����
			 * ������һ�����Ѿ�������ƷΪ0 
			 * ��sql�Ѿ�ִ�гɹ��ˣ��ֽ�����Ϊ0����Ʒ����һ��
			 * ��ͳ��������ݴ��������
			 */
			int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
			if (updateCount <= 0) {
				// û����ɱ�ɹ�(��治�㣬��ɱ��̫��)��û�и��¼�¼�������ٿ�棩
				throw new SeckillCloseException("goods is lack, seckill is closed");
			} else {
				// ��ɱ�ɹ�������¼�������Ϊ
				int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
				if (insertCount <= 0) {
					// �ظ���ɱ�����ݿ��м�¼��Ψһ
					throw new RepeatKillException("seckill repeated");
				} else {
					// ��ɱ�ɹ�
					SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
					return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
				}
			}
		} catch (SeckillCloseException e1) {
			throw e1;
		} catch (RepeatKillException e2){
			throw e2;
		} catch (Exception e) {
			//�����쳣ȫ������,�����쳣ת��Ϊ�������쳣
			//spring�������rollback
			throw new SeckillException("seckill inner error" + e.getMessage());
		}
	}

	@Override
	public SeckillExecution executeSeckillProduce(long seckillId, long userPhone, String md5) {
		
		if (md5 == null || !md5.equals(getMd5(seckillId))) {
			return new SeckillExecution(seckillId, SeckillStatEnum.DATA_REWRITE);
		}
		Date killTime = new Date();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("seckillId", seckillId);
		map.put("phone", userPhone);
		map.put("killTime", killTime);
		map.put("result", null);
		// ִ�д洢���̣�result����ֵ
		try {
			seckillDao.killByProcedure(map);
			// ��ȡresult
			int result = MapUtils.getInteger(map, "result", -2);
			if (result == 1) {
				SuccessKilled sk = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
				return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, sk);
			} else {
				return new SeckillExecution(seckillId, SeckillStatEnum.stateOf(result));
			}
		} catch (Exception e) {
			return new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
		}
	}
}
























