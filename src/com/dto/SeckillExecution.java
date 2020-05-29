package com.dto;

import com.entity.SuccessKilled;
import com.enums.SeckillStatEnum;

/**
 * ��װ��ɱ��Ľ��
 * ����ɹ�����ʧ�ܣ�
 * 
 * @author wenbochang
 * @date 2018��1��12��
 */

public class SeckillExecution {

	// id
	private long seckillId;

	// ��ɱ��״̬
	private int state;

	// ״̬�ı�ʾ
	private String stateInfo;

	// ����ɹ����ͷ��سɹ��Ķ���
	private SuccessKilled successKilled;

	public SeckillExecution(long seckillId, int state, String stateInfo, SuccessKilled successKilled) {
		super();
		this.seckillId = seckillId;
		this.state = state;
		this.stateInfo = stateInfo;
		this.successKilled = successKilled;
	}
	
	//������ɱ�ɹ��Ĺ��췽��
	public SeckillExecution(long seckillId, SeckillStatEnum statEnum, SuccessKilled successKilled) {
		this.seckillId = seckillId;
		this.state = statEnum.getState();
		this.stateInfo = statEnum.getStateInfo();
		this.successKilled = successKilled;
	}

	//������ɱʧ�ܵĹ��췽��
	public SeckillExecution(long seckillId, SeckillStatEnum statEnum) {
		super();
		this.seckillId = seckillId;
		this.state = statEnum.getState();
		this.stateInfo = statEnum.getStateInfo();
	}

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public SuccessKilled getSuccessKilled() {
		return successKilled;
	}

	public void setSuccessKilled(SuccessKilled successKilled) {
		this.successKilled = successKilled;
	}

	@Override
	public String toString() {
		return "SeckillExecution [seckillId=" + seckillId + ", state=" + state + ", stateInfo=" + stateInfo
				+ ", successKilled=" + successKilled + "]";
	}

}
