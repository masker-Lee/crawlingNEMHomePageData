package com.updateData;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

public class AutoRun implements ServletContextListener{
	//日志
//	 private static Logger logger = Logger.getLogger(AutoRun.class); 

	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void contextInitialized(ServletContextEvent arg0) {
		
		// TODO Auto-generated method stub
		ScheduledThreadPoolExecutor scheduled = new ScheduledThreadPoolExecutor(2);// new一个定时器
		
		
		
		// 定时更新数据库
		scheduled.scheduleAtFixedRate(new Runnable() {
			public void run() {
				//开启定时任务
//				logger.info("AutoRun:开始更新Users");
				StringBuilder sb;
				try {
					GetNEMHomepageData getData = new GetNEMHomepageData();
					sb = getData.getWebSource("https://supernodes.nem.io/", "UTF-8");
					UpdateDBData updateDBData = new UpdateDBData();
					updateDBData.updateDataStructure(sb.toString().trim());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
//					logger.info("AutoRun:Users获取数据或更新数据库时发生异常");
				}
//				logger.info("AutoRun:Users数据更新完毕");
			}
		}, 0, 3 * 60 * 60 * 1000, TimeUnit.MILLISECONDS);// 0表示首次执行任务的延迟时间，第二个表示每次执行任务的间隔时间，TimeUnit.MILLISECONDS执行的时间间隔数值单位
		scheduled.scheduleAtFixedRate(new Runnable() {
			public void run() {
				// 开启定时任务
//				logger.info("AutoRun:开始更新Usersdetail");
				try {
					UpdateDBData2 ud = new UpdateDBData2();
					ud.updateDataStructure();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
//					logger.info("AutoRun:Usersdetail获取数据或更新数据库时发生异常");
				}
//				logger.info("AutoRun:Usersdetail数据更新完毕");
			}
		}, 5000, 8 * 60 * 60 * 1000, TimeUnit.MILLISECONDS);// 0表示首次执行任务的延迟时间，第二个表示每次执行任务的间隔时间，TimeUnit.MILLISECONDS执行的时间间隔数值单位
	
	
	
	
	
	
	}

}
