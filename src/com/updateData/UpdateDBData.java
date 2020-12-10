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
		System.out.println("��ʼ����Users����");
		// ����������ʽ�Ի�ȡ����ҳԴ���������ƥ��
		String[] info = str.split("</tr>");
		AccessMongoDB conn = new AccessMongoDB();
		try {
			MongoCollection<Document> db = conn.getConnection("NEMDB","NEMUsers");
			for (String s : info) {
				Matcher m = proInfo.matcher(s);
				if (m.find()) {
					// ������õ�����,�����ݼ���p
					Account p = new Account();
					int id = new Integer(m.group(1).trim().replace(" ", ""));
					p.setId(id);
					p.setIp(m.group(2).trim().replace(" ", ""));
					p.setName(m.group(3).trim().replace(" ", ""));
					p.setStatus(m.group(4).trim().replace(" ", ""));
					// ��p��װ��Document��ʽ
					Document doc = Document.parse(p.toString());
					// �������ݿ����Ƿ��б���id
					FindIterable<Document> iterables = db.find(new Document("_id", id));
					MongoCursor<Document> cursor = iterables.iterator();
					// Document doc1 = cursor.next();
					if (!(cursor.hasNext())) {
						// ���û�д�id������,����doc
						db.insertOne(doc);
					} else if (!(cursor.next().equals(doc))) {
						// ����д�id,�Ա������Ƿ���ͬ,����ͬʱ�滻Ϊ������
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
