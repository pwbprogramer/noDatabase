package com.berheley.ichart.socketIo.impl;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.berheley.ichart.socketIo.UserOffLineService;
import com.berheley.ichart.socketIo.model.TMessageBean;
@Service
@SuppressWarnings("unchecked")
public class UserOffLineServiceImpl implements UserOffLineService{
    
	@SuppressWarnings("rawtypes")
	@Resource(name="redisTemplate")
	private RedisTemplate redis;
	
	private static Map<String,LinkedList<TMessageBean>> redisErrorMessage = new ConcurrentHashMap<String,LinkedList<TMessageBean>>();
	
	/**
	 * 将离线消息存入redis
	 */
	@Override
	public void saveMessageInRedis(TMessageBean messageBean) {
		String keyValue = messageBean.getToClient();
		try {
			redis.opsForList().leftPush(keyValue, messageBean);
		}catch(Exception e) {
			writeMapWhenRedisError(messageBean);
			e.printStackTrace();
		}
	}
	
	/**
	 * 读取离线消息redis
	 */
	public LinkedList<TMessageBean> readMessageInRedis(TMessageBean messageBean) {
		try {
			/**
			 * 读取消息
			 */
			LinkedList<TMessageBean> value = new LinkedList<TMessageBean>();
			ListOperations<String, TMessageBean> list= redis.opsForList();
			long size = list.size(messageBean.getFromClient());
			while(size!=0) {
				TMessageBean temp = (TMessageBean) list.leftPop(messageBean.getFromClient());
				value.add(temp);
				--size;
			}
			LinkedList<TMessageBean> temp = readMap(messageBean);
			if(temp!=null) {
				value.addAll(temp);
			}
			return value;
		}catch(Exception e) {
			e.printStackTrace();
			return readMap(messageBean);
		}
	}
	
	/**
	 * 读取redis失去连接时的暂存消息
	 * @param messageBean
	 * @return
	 */
	private LinkedList<TMessageBean> readMap(TMessageBean messageBean) {
	    if(redisErrorMessage.size()!=0) {
	    	if(redisErrorMessage.containsKey(messageBean.getFromClient())) {
	    		LinkedList<TMessageBean> temp = redisErrorMessage.get(messageBean.getFromClient());
	    		redisErrorMessage.remove(messageBean.getFromClient());
	    		return temp;
	    	}
	    }
	    return null;
	}
	
	/**
	 * redis失去连接时，将消息存入map
	 * @param messageBean
	 */
	private void writeMapWhenRedisError(TMessageBean messageBean) {
		String keyValue = messageBean.getToClient();
		if(redisErrorMessage.containsKey(keyValue)) {
			LinkedList<TMessageBean> temp = redisErrorMessage.get(keyValue);
			temp.add(messageBean);
			redisErrorMessage.put(keyValue, temp);
		}else {
			LinkedList<TMessageBean> temp = new LinkedList<TMessageBean>();
			temp.add(messageBean);
			redisErrorMessage.put(keyValue, temp);
		}
	}

}
