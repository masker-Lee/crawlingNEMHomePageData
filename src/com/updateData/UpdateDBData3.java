package com.updateData;

import org.bson.Document;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.util.AccessMongoDB;
import com.util.HttpTest;

public class UpdateDBData3 {

	public UpdateDBData3(int id) {
		AccessMongoDB conn = new AccessMongoDB();
		try {// 获得集合NEMUsersDetail
			MongoCollection<Document> db2 = conn.getConnection("NEMDB", "NEMUsersDetail");

			// Jsoup连接网站获得表格
			org.jsoup.nodes.Document doc = Jsoup.connect("https://supernodes.nem.io/details/" + id).get();
			// 获取最后一个表格
			Element element = doc.select("table").last();
			// 以"tr"分段
			Elements els = element.select("tr");
			for (Element el : els) {
				// System.out.println("分段:"+el);
				Elements ele = el.select("td");// 以"td"分段
				// 创建documentData,最后插入数据库
				Document documentData = new Document();
				// documentData中插入id
				documentData.append("id", id);
				int i = 0;
				// 以下是在documentData内添加数据
				for (Element aa : ele) {
					// 循环添加部分需要的数据
					switch (i) {
					case 0:
						documentData.append("row", aa.text());
						break;
					case 1:
						documentData.append("date", aa.text());
						break;
					case 2:
						documentData.append("alias", aa.text());
						break;
					case 3:
						documentData.append("bandwidthColor", aa.attr("style"));
						break;
					case 4:
						documentData.append("heightColor", aa.attr("style"));
						break;
					case 5:
						if ("<i class=\"fa fa-check\"></i>".equals(aa.html())) {
							documentData.append("chainPart", "PASS");
						} else {
							documentData.append("chainPart", "FAIL");
						}
						break;
					case 6:
						documentData.append("computingPowerColor", aa.attr("style"));
						break;	
					case 7:
						if ("<i class=\"fa fa-check\"></i>".equals(aa.html())) {
							documentData.append("balance", "PASS");
						} else {
							documentData.append("balance", "FAIL");
						}
						break;
					case 8:
						if ("<i class=\"fa fa-check\"></i>".equals(aa.html())) {
							documentData.append("version", "PASS");
						} else {
							documentData.append("version", "FAIL");
						}
						break;
					case 9:
						documentData.append("pingColor", aa.attr("style"));
						break;
					case 10:
						documentData.append("responsivenessColor", aa.attr("style"));
						break;
					case 11:
						documentData.append("round", Integer.parseInt(aa.text()));
						break;
					case 12:
						documentData.append("result", aa.text());
						break;
					default:
						break;
					}
					i++;
				}
				if (i == 13) {
					// 循环结束,获得round
					int round = documentData.getInteger("round");
					// 将id与round添加至查询条件
					Document docSearch = new Document();
					docSearch.append("id", id);
					docSearch.append("round", round);

					// 查找数据库NEMDBdetail内是否有本条id与round
					FindIterable<Document> iterables = db2.find(docSearch);
					MongoCursor<Document> cursor = iterables.iterator();
					if (!(cursor.hasNext())) {
						// 如果没有此id的数据,插入documentData
						String webData = HttpTest.sendPost("https://supernodes.nem.io/resultInfo", id, round);
						try {
							JSONObject json = new JSONObject(webData);
							// String bandwidth;
							try {
								documentData.append("bandwidth", String.valueOf(
										json.getJSONArray("bandwidthResults").getJSONObject(0).getInt("speed")));
							} catch (Exception e) {
								documentData.append("bandwidth", "undefined");
							}
							// String height;
							try {
								documentData.append("height", json.getJSONArray("chainHeightResults").getJSONObject(0)
										.getString("reportedHeight"));
							} catch (Exception e) {
								documentData.append("height", "undefined");
							}
							// String computingPower;
							try {
								documentData.append("computingPower", String.valueOf(json
										.getJSONArray("computingPowerResults").getJSONObject(0).getInt("timeNeeded")));
							} catch (Exception e) {
								documentData.append("computingPower", "undefined");
							}
							// String ping;
							try {
								documentData.append("ping", String.valueOf(
										json.getJSONArray("pingResults").getJSONObject(0).getInt("averageTime")));
							} catch (Exception e) {
								documentData.append("ping", "undefined");
							}
							// String responsiveness;
							try {
								documentData.append("responsiveness", String.valueOf(json
										.getJSONArray("responsivenessResults").getJSONObject(0).getInt("totalTime")));
							} catch (Exception e) {
								documentData.append("responsiveness", "undefined");
							}
							// String numRequests;
							try {
								documentData.append("numRequests", String.valueOf(json
										.getJSONArray("responsivenessResults").getJSONObject(0).getInt("numRequests")));
							} catch (Exception e) {
								documentData.append("numRequests", "undefined");
							}
							// String numResponses;
							try {
								documentData.append("numResponses",
										String.valueOf(json.getJSONArray("responsivenessResults").getJSONObject(0)
												.getInt("numResponses")));
							} catch (Exception e) {
								documentData.append("numResponses", "undefined");
							}
							db2.insertOne(documentData);

						} catch (Exception e) {
							e.printStackTrace();
						}

					}

				}
			}
		} catch (Exception e) {

		} finally {
			conn.closeMongoClient();
		}

	}

}
