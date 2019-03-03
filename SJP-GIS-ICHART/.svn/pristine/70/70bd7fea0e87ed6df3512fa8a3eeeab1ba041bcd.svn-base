package com.berheley.ichart.socketIo;

import java.util.LinkedList;

import com.berheley.ichart.socketIo.model.TMessageBean;

/**
 * 用户不在线
 * @author pwb
 */
public interface UserOffLineService {
	
	/**
	 * 存储信息
	 * @param messageBean
	 */
	public void saveMessageInRedis(TMessageBean messageBean);
	
	/**
	 * 读取消息
	 */
	public LinkedList<TMessageBean> readMessageInRedis(TMessageBean messageBean);

}
