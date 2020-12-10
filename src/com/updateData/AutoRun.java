package com.updateData;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

public class AutoRun implements ServletContextListener{
	//��־
//	 private static Logger logger = Logger.getLogger(AutoRun.class); 

	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void contextInitialized(ServletContextEvent arg0) {
		
		// TODO Auto-generated method stub
		ScheduledThreadPoolExecutor scheduled = new ScheduledThreadPoolExecutor(2);// newһ����ʱ��
		
		
		
		// ��ʱ�������ݿ�
		scheduled.scheduleAtFixedRate(new Runnable() {
			public void run() {
				//������ʱ����
//				logger.info("AutoRun:��ʼ����Users");
				StringBuilder sb;
				try {
					GetNEMHomepageData getData = new GetNEMHomepageData();
					sb = getData.getWebSource("https://supernodes.nem.io/", "UTF-8");
					UpdateDBData updateDBData = new UpdateDBData();
					updateDBData.updateDataStructure(sb.toString().trim());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
//					logger.info("AutoRun:Users��ȡ���ݻ�������ݿ�ʱ�����쳣");
				}
//				logger.info("AutoRun:Users���ݸ������");
			}
		}, 0, 3 * 60 * 60 * 1000, TimeUnit.MILLISECONDS);// 0��ʾ�״�ִ��������ӳ�ʱ�䣬�ڶ�����ʾÿ��ִ������ļ��ʱ�䣬TimeUnit.MILLISECONDSִ�е�ʱ������ֵ��λ
		scheduled.scheduleAtFixedRate(new Runnable() {
			public void run() {
				// ������ʱ����
//				logger.info("AutoRun:��ʼ����Usersdetail");
				try {
					UpdateDBData2 ud = new UpdateDBData2();
					ud.updateDataStructure();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
//					logger.info("AutoRun:Usersdetail��ȡ���ݻ�������ݿ�ʱ�����쳣");
				}
//				logger.info("AutoRun:Usersdetail���ݸ������");
			}
		}, 5000, 8 * 60 * 60 * 1000, TimeUnit.MILLISECONDS);// 0��ʾ�״�ִ��������ӳ�ʱ�䣬�ڶ�����ʾÿ��ִ������ļ��ʱ�䣬TimeUnit.MILLISECONDSִ�е�ʱ������ֵ��λ
	
	
	
	
	
	
	}

}
