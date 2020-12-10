package com.updateData;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetNEMHomepageData {

	

	

	public  StringBuilder getWebSource(String urlInfo, String charset) throws Exception {
		// ��ȡĿ����ҳURL��ַ����ȡ��ҳԴ��
		URL url = new URL(urlInfo);
		HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();
		// ����ͨ�õ���������
		httpUrl.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		httpUrl.setConnectTimeout(10000);
		httpUrl.setReadTimeout(5000);
		// httpUrl.setRequestMethod("GET");
		InputStream is = httpUrl.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, charset));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) {
			// �����Ƕ����ӽ��д���
			line = line.replaceAll("</?a[^>]*>", "");
			// �����Ƕ���ʽ���д���
			line = line.replaceAll("<(\\w+)[^>]*>", "<$1>");
			sb.append(line);
		}
		is.close();
		br.close();
		// �����ҳԴ��
		// System.out.println(sb.toString().trim());
		return sb;
	}

	

}
