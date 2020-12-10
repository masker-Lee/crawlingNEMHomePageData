package com.updateData;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.json.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.util.AccessMongoDB;

public class UpdateDBData2 {
	//日志
//		 private static Logger logger = Logger.getLogger(UpdateDBData2.class); 

	public void updateDataStructure() throws Exception  {
//	logger.info("Usersdetail程序开始");
	AccessMongoDB conn = new AccessMongoDB();
	try {
		//获得集合NEMUsers
		MongoCollection<Document> db = conn.getConnection("NEMDB","NEMUsers");
		//查询数据库NEMEDB,返回所有id
		FindIterable<Document> find = db.find().projection(new BasicDBObject().append("_id", 1)).noCursorTimeout(true);
		//获得迭代器
		MongoCursor<Document> fi = find.iterator();
		//迭代所有id
		while (fi.hasNext()) {
			JSONObject jsonfi = new JSONObject(fi.next());
			//获得当前id
			int id = jsonfi.getInt("_id");
			new UpdateDBData3(id);
			
			Thread.sleep(20*1000);
		} 
	} finally {
		conn.closeMongoClient();
//		logger.info("Usersdetail程序finally终止!");
	}
//	logger.info("Usersdetail程序结束!");
	}
}
