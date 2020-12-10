package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.bean.Account;
import com.bean.AccountWebDetail;
import com.bean.AccountWebDetail2;
import com.bean.DataVO;
import com.dao.NEMDAO;
import com.google.gson.Gson;
import com.impl.NEMDAOImpl;
import com.mongodb.client.MongoCursor;
import com.updateData.GetNEMHomepageData;
import com.updateData.UpdateDBData3;
import com.util.AccessMongoDB;
import com.util.Factory;

public class ActionServlet extends HttpServlet {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	Properties properties = new Properties();

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		properties.load(getServletContext().getResourceAsStream("/WEB-INF/token.properties"));

//		String token = properties.getProperty("token");
//		String urlToken = request.getParameter("token");
//		if(token.equals(urlToken)) {
		String uri = request.getRequestURI();
		String action = uri.substring(uri.lastIndexOf("/"), uri.lastIndexOf("."));
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");

		if (action.contentEquals("/index")) {
			try {
				// ����ҳ��ȡ����
				List<Account> accounts = new ArrayList<Account>();
				try {
					Pattern proInfo = Pattern.compile(
							"<td>(.*?)</td>\\s*<td>(.*?)</td>\\s*<td>(.*?)</td>\\s*<td>(.*?)</td>", Pattern.DOTALL);

					GetNEMHomepageData getData = new GetNEMHomepageData();
					String str = getData.getWebSource("https://supernodes.nem.io/", "UTF-8").toString().trim();
					// ����������ʽ�Ի�ȡ����ҳԴ���������ƥ��
					String[] info = str.split("</tr>");
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
							accounts.add(p);
						}
					}
				} catch (Exception e) {
					// ��ҳ��ȡʧ��,��Ϊ�����ݿ��ȡ����
					try {
						NEMDAO dao = (NEMDAO) Factory.getInstance("NEMDAO");
						accounts = dao.findAll();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						System.out.println("��ȡ����ʧ��,���Ժ�����");
					}
				}
				// ת��
				// step1,������
				request.setAttribute("accounts", accounts);
				// step2,���ת����
				RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/page/index.jsp");
				// step3,ת��
				rd.forward(request, response);

			} catch (Exception e) {
				e.printStackTrace();

			}
		} else if (action.contentEquals("/details")) {

			String[] id = request.getParameterValues("id");// ���request������id
			AccountWebDetail acc = new AccountWebDetail();// �û����ݵ�List,���ת����jsp
			acc.setId(Integer.parseInt(id[0]));
			try {
				// ʹ��jsoup��ȡ��ҳ�ı������
				org.jsoup.nodes.Document doc = Jsoup.connect("https://supernodes.nem.io/details/" + id[0]).get();
				// ��ȡ���һ�����
				Element element = doc.select("table").last();
				// ��"tr"�ֶ�
				Elements els = element.select("tr");

				Elements ele = els.select("td");// ��"td"�ֶ�

				int i = 0;
				for (Element aa : ele) {
					// ѭ����acc����Ӳ�������
					switch (i) {
					case 2:
						acc.setAlias(aa.text());
						break;
					case 11:
						acc.setRound(Integer.parseInt(aa.text()));
						break;
					default:
						break;
					}
					i++;

				}
			} catch (Exception e) {

			}

			// ת��
			// step1,������
			request.setAttribute("acc", acc);
			// step2,���ת����
			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/page/detail.jsp");
			// step3,ת��
			rd.forward(request, response);
			
		} else if (action.contentEquals("/round")) {

			String[] round = request.getParameterValues("round");// ���request������round
			
			List<AccountWebDetail2> accList = new ArrayList<AccountWebDetail2>();// �û����ݵ�List,���ת����jsp
					try {
						// �������ݿ�
						AccessMongoDB conn = new AccessMongoDB();
						// ��ü���NEMUsersDetail
						MongoCursor<Document> mongoCursor = conn.getConnection("NEMDB", "NEMUsersDetail")
								.find(new Document("round", Integer.parseInt(round[0]))).sort(new Document("row",1)).iterator();
						
						// ѭ������mongoCursor
						while (mongoCursor.hasNext()) {
							//���mongoCursor�ڵ�����,�������ݸ�ʽΪString
							String data = mongoCursor.next().toJson();
							//��String��ʽת��ΪJSON��ʽ
							JSONObject dataJson = new JSONObject(data);
							
							AccountWebDetail2 acc = new AccountWebDetail2();// �û����ݵ�List,���ת����jsp
							acc.setId(dataJson.getInt("id"));
							acc.setRow(dataJson.getString("row"));
							acc.setDate(dataJson.getString("date"));
							acc.setAlias(dataJson.getString("alias"));

							String bandwidthColor = "color:green";
							try {
								bandwidthColor = dataJson.getString("bandwidthColor");
							} catch (Exception e) {

							}
							String bandwidth = dataJson.getString("bandwidth");
							if ("undefined".equals(bandwidth)) {
								bandwidth = "<span style=\"color:red\"><i  class=\"fa fa-times\"></i></span>";
							} else {
								bandwidth = "<span style=\"" + bandwidthColor + "\">" + bandwidth + " Kbit/s</span>";
							}
							acc.setBandwidth(bandwidth);

							String heightColor = "color:green";
							try {
								heightColor = dataJson.getString("heightColor");
							} catch (Exception e) {

							}
							String height = dataJson.getString("height");
							if ("undefined".equals(height)) {
								acc.setHeight("<i style=\"color:red\" class=\"fa fa-times\"></i>");
							} else {
								acc.setHeight("<span style=\"" + heightColor + "\">" + height + "</span>");
							}

							String chainPart = dataJson.getString("chainPart");
							if("PASS".equals(chainPart)) {
								acc.setChainPart("<i style=\"color:green\" class=\"fa fa-check\"></i>");
							}else {
								acc.setChainPart("<i style=\"color:red\" class=\"fa fa-times\"></i>");
							}
							

							String computingPowerColor = "color:green";
							try {
								computingPowerColor = dataJson.getString("computingPowerColor");
							} catch (Exception e) {

							}
							String computingPower = dataJson.getString("computingPower");
							if ("undefined".equals(computingPower)) {
								acc.setComputingPower("<i style=\"color:red\" class=\"fa fa-times\"></i>");
							} else {
								acc.setComputingPower(
										"<span style=\"" + computingPowerColor + "\">" + computingPower + " ms</span>");
							}

							if("PASS".equals(dataJson.getString("balance"))) {
								acc.setBalance("<i style=\"color:green\" class=\"fa fa-check\"></i>");
							}else {
								acc.setBalance("<i style=\"color:red\" class=\"fa fa-times\"></i>");
							}
							
							if("PASS".equals(dataJson.getString("version"))) {
								acc.setVersion("<i style=\"color:green\" class=\"fa fa-check\"></i>");
							}else {
								acc.setVersion("<i style=\"color:red\" class=\"fa fa-times\"></i>");
							}

							String pingColor = "color:green";
							try {
								pingColor = dataJson.getString("pingColor");
							} catch (Exception e) {

							}
							String ping = dataJson.getString("ping");
							if ("undefined".equals(ping)) {
								acc.setPing("<i style=\"color:red\" class=\"fa fa-times\"></i>");
							} else {
								acc.setPing("<span style=\"" + pingColor + "\">" + ping + " ms</span>");
							}

							String responsivenessColor = "color:green";
							try {
								responsivenessColor = dataJson.getString("responsivenessColor");
							} catch (Exception e) {

							}
							String responsiveness = dataJson.getString("responsiveness");
							if ("undefined".equals(responsiveness)) {
								acc.setResponsiveness("<i style=\"color:red\" class=\"fa fa-times\"></i>");
							} else {
								acc.setResponsiveness("<span style=\"" + responsivenessColor + "\">" + responsiveness + " ms ("
										+ dataJson.getString("numResponses") + "/" + dataJson.getString("numRequests")
										+ ") </span>");
							}

							acc.setRound(dataJson.getInt("round"));

							if("PASS".equals(dataJson.getString("result"))){
								acc.setResult("<span style=\"color:green;font-weight: bold\">PASS</span>");
							}else{
								acc.setResult("<span style=\"color:red;font-weight: bold\">FAIL</span>");
							}
							
							// ��acc2���뵽accList
							accList.add(acc);
							
						}
							conn.closeMongoClient();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			
			// ת��
			// step1,������
			request.setAttribute("accList", accList);
			// step2,���ת����
			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/page/round.jsp");
			// step3,ת��
			rd.forward(request, response);
			System.out.println("ҳ����ʾ�ɹ�");
		} else if (action.contentEquals("/data")) {

			System.out.println("��������������");
			// For pagination
			int pageSize = 10;
			int startRecord = 0;
			String[] requestid = request.getParameterValues("id");// ���request������id
			int id = Integer.parseInt(requestid[0]);
			// �����������ݿ�

			try {
				new UpdateDBData3(id);
				System.out.println("�����������ݿ�ɹ�!");
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
				System.out.println("�����������ݿ�ʧ��!");
			}
			String size = request.getParameter("length");
			if (!"".equals(size) && size != null) {
				pageSize = Integer.parseInt(size);
			}
			String currentRecord = request.getParameter("start");
			if (!"".equals(currentRecord) && currentRecord != null) {
				startRecord = Integer.parseInt(currentRecord);
			}
			int counter = 0;
			NEMDAO dao = new NEMDAOImpl();
			try {
				counter = dao.countUserDetail(id);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			List<AccountWebDetail2> accList = new ArrayList<AccountWebDetail2>();// �û����ݵ�List,���ת����jsp
			// �������ݿ�
			AccessMongoDB conn = new AccessMongoDB();
			try {
				MongoCursor<Document> mongoCursor = null;
				
					// ��ü���NEMUsersDetail
					mongoCursor = conn.getConnection("NEMDB", "NEMUsersDetail").find(new Document("id", id))
							.sort(new Document("round", -1)).skip(startRecord*pageSize).limit(pageSize).iterator();
				
				// ѭ������mongoCursor
				while (mongoCursor.hasNext()) {
					// ���mongoCursor�ڵ�����,�������ݸ�ʽΪString
					String data = mongoCursor.next().toJson();
					// ��String��ʽת��ΪJSON��ʽ
					JSONObject dataJson = new JSONObject(data);
					// new��acc2����,����ֵ
					AccountWebDetail2 acc2 = new AccountWebDetail2();
					acc2.setId(dataJson.getInt("id"));
					acc2.setRow(dataJson.getString("row"));
					acc2.setDate(dataJson.getString("date"));
					acc2.setAlias(dataJson.getString("alias"));

					String bandwidthColor = "color:green";
					try {
						bandwidthColor = dataJson.getString("bandwidthColor");
					} catch (Exception e) {

					}
					String bandwidth = dataJson.getString("bandwidth");
					if ("undefined".equals(bandwidth)) {
						bandwidth = "<span style=\"color:red\"><i  class=\"fa fa-times\"></i></span>";
					} else {
						bandwidth = "<span style=\"" + bandwidthColor + "\">" + bandwidth + " Kbit/s</span>";
					}
					acc2.setBandwidth(bandwidth);

					String heightColor = "color:green";
					try {
						heightColor = dataJson.getString("heightColor");
					} catch (Exception e) {

					}
					String height = dataJson.getString("height");
					if ("undefined".equals(height)) {
						acc2.setHeight("<i style=\"color:red\" class=\"fa fa-times\"></i>");
					} else {
						acc2.setHeight("<span style=\"" + heightColor + "\">" + height + "</span>");
					}

					String chainPart = dataJson.getString("chainPart");
					if("PASS".equals(chainPart)) {
						acc2.setChainPart("<i style=\"color:green\" class=\"fa fa-check\"></i>");
					}else {
						acc2.setChainPart("<i style=\"color:red\" class=\"fa fa-times\"></i>");
					}
					

					String computingPowerColor = "color:green";
					try {
						computingPowerColor = dataJson.getString("computingPowerColor");
					} catch (Exception e) {

					}
					String computingPower = dataJson.getString("computingPower");
					if ("undefined".equals(computingPower)) {
						acc2.setComputingPower("<i style=\"color:red\" class=\"fa fa-times\"></i>");
					} else {
						acc2.setComputingPower(
								"<span style=\"" + computingPowerColor + "\">" + computingPower + " ms</span>");
					}

					if("PASS".equals(dataJson.getString("balance"))) {
						acc2.setBalance("<i style=\"color:green\" class=\"fa fa-check\"></i>");
					}else {
						acc2.setBalance("<i style=\"color:red\" class=\"fa fa-times\"></i>");
					}
					
					if("PASS".equals(dataJson.getString("version"))) {
						acc2.setVersion("<i style=\"color:green\" class=\"fa fa-check\"></i>");
					}else {
						acc2.setVersion("<i style=\"color:red\" class=\"fa fa-times\"></i>");
					}

					String pingColor = "color:green";
					try {
						pingColor = dataJson.getString("pingColor");
					} catch (Exception e) {

					}
					String ping = dataJson.getString("ping");
					if ("undefined".equals(ping)) {
						acc2.setPing("<i style=\"color:red\" class=\"fa fa-times\"></i>");
					} else {
						acc2.setPing("<span style=\"" + pingColor + "\">" + ping + " ms</span>");
					}

					String responsivenessColor = "color:green";
					try {
						responsivenessColor = dataJson.getString("responsivenessColor");
					} catch (Exception e) {

					}
					String responsiveness = dataJson.getString("responsiveness");
					if ("undefined".equals(responsiveness)) {
						acc2.setResponsiveness("<i style=\"color:red\" class=\"fa fa-times\"></i>");
					} else {
						acc2.setResponsiveness("<span style=\"" + responsivenessColor + "\">" + responsiveness + " ms ("
								+ dataJson.getString("numResponses") + "/" + dataJson.getString("numRequests")
								+ ") </span>");
					}

					acc2.setRound(dataJson.getInt("round"));

					if("PASS".equals(dataJson.getString("result"))){
						acc2.setResult("<span style=\"color:green;font-weight: bold\">PASS</span>");
					}else{
						acc2.setResult("<span style=\"color:red;font-weight: bold\">FAIL</span>");
					}
					
					// ��acc2���뵽accList
					accList.add(acc2);

				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			conn.closeMongoClient();
			DataVO<AccountWebDetail2> result = new DataVO<AccountWebDetail2>();
			result.setDraw(
					Integer.parseInt(request.getParameter("draw") == null ? "0" : request.getParameter("draw")) + 1);
			result.setData(accList);
			result.setRecordsTotal(counter);
			result.setRecordsFiltered(counter);
			Gson gson = new Gson();
			String output = gson.toJson(result);
			//System.out.println("Output JSON: \n" + output);
			PrintWriter out = response.getWriter();
			out.write(output);
			out.flush();
			out.close();
		}
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request  the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException      if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

}
