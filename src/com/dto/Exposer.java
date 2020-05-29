package com.dto;

/**
 * @author wenbochang
 * @date 2018��1��12��
 */

/**
 * ��¶��ɱ�ĵ�ַ�������Ǹ���ť
 */

public class Exposer {

	//�Ƿ�����ɱ
	private boolean exposed;

	//�����㷨��url��ַ����ƴ��
	private String md5;
	
	//id
	private long seckillId;
	
	//ϵͳ��ǰ��ʱ��
	private long now;
	
	//����ʱ��
	private long start;
	
	//�ر�ʱ��
	private long end;

	//���Խ�����ɱ��md5��֤
	public Exposer(boolean exposed, String md5, long seckillId) {
		super();
		this.exposed = exposed;
		this.md5 = md5;
		this.seckillId = seckillId;
	}

	//��û�е���ɱ��ʼ��ʱ��
	public Exposer(boolean exposed, long seckillId, long now, long start, long end) {
		super();
		this.exposed = exposed;
		this.seckillId = seckillId;
		this.now = now;
		this.start = start;
		this.end = end;
	}

	//id�鲻���������ݿ�����û��������¼
	public Exposer(boolean exposed, long seckillId) {
		super();
		this.exposed = exposed;
		this.seckillId = seckillId;
	}

	public boolean isExposed() {
		return exposed;
	}

	public void setExposed(boolean exposed) {
		this.exposed = exposed;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public long getNow() {
		return now;
	}

	public void setNow(long now) {
		this.now = now;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return "Exposer [exposed=" + exposed + ", md5=" + md5 + ", seckillId=" + seckillId + ", now=" + now + ", start="
				+ start + ", end=" + end + "]";
	}
}













