package com.test;

/**
 * @author wenbochang
 * @date 2018��1��12��
 */

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dao.SeckillDao;
import com.dao.cache.RedisDao;
import com.entity.Seckill;

/**
 * @author Chang-pc
 * 
 * ����ʱ����IoC����
 */

@ContextConfiguration({ "classpath:config/spring-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SeckillDaoTest {

	@Autowired
	private SeckillDao seckillDao;

	@Test
	public void testQueryById() throws Exception {

		long id = 1000;
		Seckill seckill = seckillDao.queryById(id);
		System.out.println(seckill);
	}

	/**
	 * �����ݶ��������ʱ��
	 * mybatis������ʶ���Ǹ��������Ǹ�����
	 * ���Ե�һ�ַ���������map��
	 * �ڶ��ַ���������@Param("value")����valueָ������������
	 */
	@Test
	public void testQueryAll() throws Exception {

//		Map<String, Object> map = new HashMap<>();
//		map.put("offset", 0);
//		map.put("limit", 2);
//		List<Seckill> seckills = seckillDao.queryAll(map);
		List<Seckill> seckills = seckillDao.queryAll(0, 10);
		for (Seckill seckill : seckills) {
			System.out.println(seckill);
		}
	}

	@Test
	public void testReduceNumber() throws Exception {

		Date killTime = new Date();
		int updateCount = seckillDao.reduceNumber(1000, killTime);
		System.out.println(updateCount);
	}
	
	@Autowired
	RedisDao redisDao;
	
	@Test
	public void testRedis() {
		//get and put
		Seckill seckill = redisDao.getSeckill(1001);
		if (seckill == null) {
			seckill = seckillDao.queryById(1001);
			if (seckill != null) {
				@SuppressWarnings("unused")
				String res = redisDao.putSeckill(seckill);
				System.out.println(res);
				seckill = redisDao.getSeckill(1001);
				System.out.println(seckill);
			}
		}
	}
}










