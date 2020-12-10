package com.util;

import com.impl.NEMDAOImpl;

/**
 *工厂可以使用配置文件来获得接口与对应实现类的配置信息，这样，
 *当实现类发生改变，不用再去修改工厂娄的源代码 
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
