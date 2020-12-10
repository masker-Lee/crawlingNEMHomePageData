package com.updateData;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetNEMHomepageData {

	

	

	public  StringBuilder getWebSource(String urlInfo, String charset) throws Exception {
		// 读取目的网页URL地址，获取网页源码
		URL url = new URL(urlInfo);
		HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();
		// 设置通用的请求属性
		httpUrl.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		httpUrl.setConnectTimeout(10000);
		httpUrl.setReadTimeout(5000);
		// httpUrl.setRequestMethod("GET");
		InputStream is = httpUrl.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, charset));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) {
			// 这里是对链接进行处理
			line = line.replaceAll("</?a[^>]*>", "");
			// 这里是对样式进行处理
			line = line.replaceAll("<(\\w+)[^>]*>", "<$1>");
			sb.append(line);
		}
		is.close();
		br.close();
		// 获得网页源码
		// System.out.println(sb.toString().trim());
		return sb;
	}

	

}
