package priv.lirenhe.spy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class Spy {
	private String url;

	public Spy(String url) {
		this.url = url;
	}

	public String sendGet() {
		CloseableHttpClient httpClient = null;
		HttpGet httpGet = null;
		RequestConfig requestConfig = null;
		HttpResponse httpResponse = null;
		HttpEntity httpEntity = null;

		// 创建httpclient，httpget，httpconfig，httpresponse
		// 设置httpget和httpconfig
		httpClient = HttpClients.createDefault();
		httpGet = new HttpGet(this.url);
		httpGet.addHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
		httpGet.addHeader("Content-type", "text/html; charset=GBK");
		httpGet.addHeader("Accept", "text/html");

		requestConfig = RequestConfig.custom().setConnectTimeout(10 * 1000).setSocketTimeout(180 * 1000).build();
		httpGet.setConfig(requestConfig);

		InputStream is = null;
		StringBuilder stringBuilder = null;
		String str = null;
		String content = null;
		try {
			httpResponse = httpClient.execute(httpGet);
			StatusLine statusLine = httpResponse.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();
				// 将输入流转换成字符串的操作
				BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
				stringBuilder = new StringBuilder();
				while ((str = br.readLine()) != null) {
					stringBuilder.append(str);
				}
				content = stringBuilder.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return content;
	}

	public String sendPost(String url, Map<String, String> requestParamPair, String encoding)
			throws UnsupportedEncodingException {

		String result = "";
		CloseableHttpClient httpclient = null;
		HttpPost httpPost = null;
		RequestConfig requestConfig = null;
		CloseableHttpResponse response = null;
		StatusLine statusLine = null;

		httpclient = HttpClients.createDefault();
		httpPost = new HttpPost(url);
		httpPost.addHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		httpPost.addHeader("Accept-Encoding", "gzip, deflate, br");
		httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
		httpPost.addHeader("Connection", "keep-alive");
		httpPost.addHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
		httpPost.addHeader("Content-type", "text/html; charset=GBK");
		requestConfig = RequestConfig.custom().setConnectTimeout(10 * 1000).setSocketTimeout(180 * 1000).build();
		httpPost.setConfig(requestConfig);

		// 设置请求参数
		List<NameValuePair> list = new ArrayList<NameValuePair>();

		if (requestParamPair != null) {
			for (Map.Entry<String, String> entry : requestParamPair.entrySet()) {
				list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		httpPost.setEntity(new UrlEncodedFormEntity(list, encoding));
		System.out.println("请求地址：" + url);
		System.out.println("请求参数：" + list.toString());

		try {
			response = httpclient.execute(httpPost);
			statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				HttpEntity httpEntity = response.getEntity();
				if (httpEntity != null) {
					InputStream is = null;
					is = httpEntity.getContent();
					BufferedReader br = new BufferedReader(new InputStreamReader(is));
					StringBuilder sb = null;
					String str = null;
					if (br != null) {
						while ((str = br.readLine()) != null) {
							sb.append(str);
						}
					}
					result = sb.toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void main(String[] args) {
		try {
			// String content =
			// getHtmlOnUrl("https://www.taobao.com/markets/3c/sj?spm=a21bo.2017.201867-main.12.15c5d928iI9B75");
			// System.out.println(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
