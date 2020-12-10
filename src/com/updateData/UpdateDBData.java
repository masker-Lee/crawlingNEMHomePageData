package com.updateData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bson.Document;

import com.bean.Account;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.util.AccessMongoDB;

public class UpdateDBData {

	static Pattern proInfo = Pattern.compile("<td>(.*?)</td>\\s*<td>(.*?)</td>\\s*<td>(.*?)</td>\\s*<td>(.*?)</td>",
			Pattern.DOTALL);
	
	public void updateDataStructure(String str) {
		System.out.println("开始更新Users数据");
		// 运用正则表达式对获取的网页源码进行数据匹配
		String[] info = str.split("</tr>");
		AccessMongoDB conn = new AccessMongoDB();
		try {
			MongoCollection<Document> db = conn.getConnection("NEMDB","NEMUsers");
			for (String s : info) {
				Matcher m = proInfo.matcher(s);
				if (m.find()) {
					// 遍历获得的数据,将数据加入p
					Account p = new Account();
					int id = new Integer(m.group(1).trim().replace(" ", ""));
					p.setId(id);
					p.setIp(m.group(2).trim().replace(" ", ""));
					p.setName(m.group(3).trim().replace(" ", ""));
					p.setStatus(m.group(4).trim().replace(" ", ""));
					// 将p封装成Document格式
					Document doc = Document.parse(p.toString());
					// 查找数据库内是否有本条id
					FindIterable<Document> iterables = db.find(new Document("_id", id));
					MongoCursor<Document> cursor = iterables.iterator();
					// Document doc1 = cursor.next();
					if (!(cursor.hasNext())) {
						// 如果没有此id的数据,插入doc
						db.insertOne(doc);
					} else if (!(cursor.next().equals(doc))) {
						// 如果有此id,对比数据是否相同,不相同时替换为新数据
						db.replaceOne(new Document("_id", id), doc);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			conn.closeMongoClient();
		}
		
	}
	
}
