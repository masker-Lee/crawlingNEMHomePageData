package com.util;

import com.impl.NEMDAOImpl;

/**
 *��������ʹ�������ļ�����ýӿ����Ӧʵ�����������Ϣ��������
 *��ʵ���෢���ı䣬������ȥ�޸Ĺ���¦��Դ���� 
 *
 */

public class Factory {
	public static Object getInstance(String type){
		Object obj = null;
		if("NEMDAO".contentEquals(type)){
			obj = new NEMDAOImpl();
		}
		return obj;
	}

}
