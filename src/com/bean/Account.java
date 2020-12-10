package com.bean;

public class Account {

		int id;
		String ip;
		String name;
		String status;
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getIp() {
			return ip;
		}
		public void setIp(String ip) {
			this.ip = ip;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}

		@Override
		public String toString() {
			return "{" + "_id:"+id + "," + "ip:'"+ip + "'," + "name:'"+name + "'," + "status:'"+status + "'}";
		}
		
		
		
}
