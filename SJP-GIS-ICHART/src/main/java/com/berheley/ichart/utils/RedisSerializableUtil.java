package com.berheley.ichart.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * redis 序列化类
 * @author pwb
 *
 */
public class RedisSerializableUtil {
	
	public static byte[] SerializData(Object thisClass) {
		if(thisClass.getClass() instanceof Serializable) {
			ByteArrayOutputStream byteArrayOutputStream = null;
			ObjectOutputStream objectOutputStream = null;
			try {
				byteArrayOutputStream = new ByteArrayOutputStream();
				objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
				objectOutputStream.writeObject(thisClass);
				return byteArrayOutputStream.toByteArray();
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				if(objectOutputStream != null) {
					try {
						objectOutputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(byteArrayOutputStream != null) {
					try {
						byteArrayOutputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		};
		return null;
	}
	
	public static Object UnSerializData(byte[] thisData) {
		ObjectInputStream objectInputStream = null;
		ByteArrayInputStream byteArrayInputStream = null;
		try {
			byteArrayInputStream = new ByteArrayInputStream(thisData);
			objectInputStream = new ObjectInputStream(byteArrayInputStream);
			return objectInputStream.readObject();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(objectInputStream!=null) {
				try {
					objectInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(byteArrayInputStream!=null) {
				try {
					byteArrayInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

}
