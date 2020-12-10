package com.impl;

import java.util.ArrayList;
import java.util.List;

import org.bson.BSON;
import org.bson.BSONObject;
import org.bson.BasicBSONObject;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONObject;

import com.bean.Account;
import com.dao.NEMDAO;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.util.AccessMongoDB;


public class NEMDAOImpl implements NEMDAO{

	public List<Account> findAll() throws Exception {
		List<Account> account = new ArrayList<Account>();
		AccessMongoDB conn = new AccessMongoDB();
		
		try{
			MongoCursor<Document> mongoCursor = conn.getConnection("NEMDB","NEMUsers").find().sort(new Document("_id",1)).iterator();
			while (mongoCursor.hasNext()) {
				String data = mongoCursor.next().toJson();
				JSONObject dataJson = new JSONObject(data);
				
				Account acc = new Account();
				acc.setId(dataJson.getInt("_id"));
				acc.setIp(dataJson.getString("ip"));
				acc.setName(dataJson.getString("name"));
				acc.setStatus(dataJson.getString("status"));
				account.add(acc);
			}
			}catch(Exception e){
				e.printStackTrace();
				throw e;
			
		}finally{
			conn.closeMongoClient();;
		}
				
		return account;
	}

	public void deleteOne(int id) throws Exception {
		AccessMongoDB conn = new AccessMongoDB();
		MongoCollection<Document> document = conn.getConnection("NEMDB","NEMUsers");
		document.deleteOne(new Document("_id", id));
	}

	public void insert(Account a) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public List<Document> findBy(Bson filter) throws Exception {
		AccessMongoDB conn = new AccessMongoDB();
		MongoCollection<Document> document = conn.getConnection("NEMDB","NEMUsers");
		List<Document> results = new ArrayList<Document>();
        FindIterable<Document> iterables = document.find(filter);
        MongoCursor<Document> cursor = iterables.iterator();
        while (cursor.hasNext()) {
            results.add(cursor.next());
        }
 
        return results;

	}

	public void update(Account e) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public long count() throws Exception {
		// TODO Auto-generated method stub
		AccessMongoDB conn = new AccessMongoDB();
		MongoCollection<Document> document = conn.getConnection("NEMDB","NEMUsers");
		long count = document.countDocuments();
		return count;
	}

	@Override
	public int countUserDetail(int id) throws Exception {
		AccessMongoDB conn = new AccessMongoDB();
		MongoCollection<Document> document = conn.getConnection("NEMDB","NEMUsersDetail");
		Bson  bs = new BasicDBObject("id",id);
		int count = (int) document.countDocuments(bs);
		return count;
	}
	
	
	/** 分页查询 */
    public MongoCursor<Document> findByPage(MongoCollection<Document> coll, Bson filter, int pageNo, int pageSize) {
        Bson orderBy = new BasicDBObject("round", 1);
        return coll.find(filter).sort(orderBy).skip((pageNo - 1) * pageSize).limit(pageSize).iterator();
    }
	

}
