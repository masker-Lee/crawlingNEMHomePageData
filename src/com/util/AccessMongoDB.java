/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;


public class AccessMongoDB {
    static MongoClient mongoClient = null;
    
    public   MongoCollection<Document> getConnection(String database,String collection) throws Exception {
		
		MongoCollection<Document> conn = null;
		try {
			//MongoCredential credential = MongoCredential.createCredential("NEMUSER1","NEMDB","NEM##321".toCharArray());//��֤����
			//MongoClientOptions options = MongoClientOptions.builder().sslEnabled(false).build(); //���Ӳ�������
			// ��ȡָ�����ݿ����
			//mongoClient = new MongoClient("127.0.0.1", 27017);
			//mongoClient = new MongoClient(new ServerAddress("127.0.0.1",27017),credential,options);   //���Ӷ���
			
			mongoClient = new MongoClient(new MongoClientURI("mongodb://NEMUSER1:NEM##321@127.0.0.1:27017/?authSource=NEMDB&ssl=false"));
			//mongoClient = new MongoClient(new MongoClientURI(uri));
			
			
			// ��ȡָ�����϶���
			conn = mongoClient.getDatabase(database).getCollection(collection);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} 

		return conn;
	}
	
	public void closeMongoClient(){
		if (mongoClient != null)
			try {
				mongoClient.close();
			} catch (Exception e) {

			}
    }
}
