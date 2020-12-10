package com.dao;

import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.bean.Account;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;


public interface NEMDAO {
	public List<Account> findAll() throws Exception;
	public void deleteOne(int id) throws Exception;
	public void insert(Account a) throws Exception;
	public List<Document> findBy(Bson filter) throws Exception;
	public void update(Account e) throws Exception;
	public long count() throws Exception;
	public int countUserDetail(int id) throws Exception;
	public MongoCursor<Document> findByPage(MongoCollection<Document> coll, Bson filter, int pageNo, int pageSize);
}
