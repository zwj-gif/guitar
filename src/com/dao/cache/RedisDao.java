package com.dao.cache;

import com.entity.Seckill;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.runtime.RuntimeSchema;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * ������л����˵�������jar��
 * protostuff
 * �����java��������л��������е�ʱ����Ӷ�
 * �ֽڸ�����
 * 
 * @author wenbochang
 * @date 2018��1��15��
 */

public class RedisDao {
	
	public RedisDao() {
		// TODO Auto-generated constructor stub
	}

	private JedisPool jedisPool;
	
	private RuntimeSchema<Seckill> schema = 
			RuntimeSchema.createFrom(Seckill.class);

	
	public RedisDao(JedisPoolConfig config, String ip, int port, String password) {
		jedisPool = new JedisPool(config, ip, port, 1000, password);
	}

	public Seckill getSeckill(long seckillId) {
		/**
		 * ��ȡredis����
		 * �õ�����һ�����������飬Ȼ����з����л�
		 * �����Զ�������л�
		 * protostuff
		 */
		try {
			Jedis jedis = jedisPool.getResource();
			try {
				String key = "seckill:" + seckillId;
				byte[] bytes = jedis.get(key.getBytes());
				if (bytes != null) {
					Seckill seckill = schema.newMessage();
					ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
					// seckill�������л�
					return seckill;
				}
			} finally {
				if (jedis != null) {
					jedis.close();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		} 
		
		return null;
	}

	public String putSeckill(Seckill seckill) {
		// set Object(Seckill) -> ���к� -> byte[]
		try {
			Jedis jedis = jedisPool.getResource();
			try {
				String key = "seckill:" + seckill.getSeckillId();
				byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema,
						LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
				// ��ʱ����
				int timeout = 60 * 60; //3600s ��һ��Сʱ
				String result = jedis.setex(key.getBytes(), timeout, bytes);
				return result;
			} finally {
				jedis.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}









