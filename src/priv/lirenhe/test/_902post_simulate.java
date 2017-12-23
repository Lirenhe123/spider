package priv.lirenhe.test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

//这个网址更好：http://blog.csdn.net/pathuang68/article/details/6920076
//自己的能力不足，暂时不取研究这些代码
public class _902post_simulate {
	public static void testPost() throws IOException {

		/**
		 * 首先要和URL下的URLConnection对话。 URLConnection可以很容易的从URL得到。比如： // Using
		 * java.net.URL and //java.net.URLConnection
		 * 
		 * 使用页面发送请求的正常流程：在页面http://www.faircanton.com/message/loginlytebox.asp中输入用户名和密码，然后按登录，
		 * 跳转到页面http://www.faircanton.com/message/check.asp进行验证 验证的的结果返回到另一个页面
		 * 
		 * 使用java程序发送请求的流程：使用URLConnection向http://www.faircanton.com/message/check.asp发送请求
		 * 并传递两个参数：用户名和密码 然后用程序获取验证结果
		 */
		URL url = new URL("http://www.faircanton.com/message/check.asp");
		URLConnection connection = url.openConnection();
		/**
		 * 然后把连接设为输出模式。URLConnection通常作为输入来使用，比如下载一个Web页。
		 * 通过把URLConnection设为输出，你可以把数据向你个Web页传送。下面是如何做：
		 */
		connection.setDoOutput(true);
		/**
		 * 最后，为了得到OutputStream，简单起见，把它约束在Writer并且放入POST信息中，例如： ...
		 */
		OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "8859_1");
		out.write("username=kevin&password=*********"); // 向页面传递数据。post的关键所在！
		// remember to clean up
		out.flush();
		out.close();
		/**
		 * 这样就可以发送一个看起来象这样的POST： POST /jobsearch/jobsearch.cgi HTTP 1.0 ACCEPT:
		 * text/plain Content-type: application/x-www-form-urlencoded
		 * Content-length: 99 username=bob password=someword
		 */
		// 一旦发送成功，用以下方法就可以得到服务器的回应：
		String sCurrentLine;
		String sTotalString;
		sCurrentLine = "";
		sTotalString = "";
		InputStream l_urlStream;
		l_urlStream = connection.getInputStream();
		// 传说中的三层包装阿！
		BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream));
		while ((sCurrentLine = l_reader.readLine()) != null) {
			sTotalString += sCurrentLine + "/r/n";

		}
		System.out.println(sTotalString);

	}

	/**
	 * 通过HTTP协议向指定的网络地址发送文件。
	 * 
	 * @param params
	 *            传输过程中需要传送的参数
	 * @param filename
	 *            需要传送的文件在本地的位置。
	 * @throws TransferException
	 */
	public String doPost(HashMap params, InputStream stream) {
		URLConnection conn = null; // URL连结对象。
		BufferedReader in = null; // 请求后的返回信息的读取对象。
		String keyName = null;
		URL url =null;
		try {
			conn = url.openConnection();
			conn.setUseCaches(false);
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "multipart/form-data");

			// 设置参数
			if (params != null) {
				Set keys = params.keySet();
				// 遍历参数集取得参数名称和值
				if (!keys.isEmpty()) {
					Iterator iterator = keys.iterator();
					while (iterator.hasNext()) {
						keyName = (String) iterator.next();
						// 将参数加入到连接对象中
						conn.addRequestProperty(keyName, (String) params.get(keyName));
					}
				}
			}
			// 构造传输文件
			// FileInputStream fis = new FileInputStream(filename);
			BufferedInputStream bis = new BufferedInputStream(stream);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int ch;
			while ((ch = bis.read()) != -1)
				baos.write(ch);
			byte[] fileData = baos.toByteArray();

			// 传输文件。
			DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(conn.getOutputStream()));
			dos.write(fileData);
			dos.flush();
			dos.close();

			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			// in.close();
		} catch (FileNotFoundException fe) {
			InputStream err = ((HttpURLConnection) conn).getErrorStream();
			if (err == null)
//				throw new TransferException("网络传输时发生的未知错误");
			in = new BufferedReader(new InputStreamReader(err));
		} catch (IOException ioe) {
			ioe.printStackTrace();
//			throw new TransferException("网络传输错误！");
		}

		// 返回提示信息
		StringBuffer response = new StringBuffer();
		String line;
		try {
			while ((line = in.readLine()) != null)
				response.append(line + "/n");
			in.close();
		} catch (IOException ioe) {
			ioe.getStackTrace();
//			throw new TransferException("网络响应错误！");
		}
		return response.toString();
	}

	/**
	 * 通过HTTP协议向指定的网络地址发送文件。
	 * 
	 * @param params
	 *            传输过程中需要传送的参数
	 * @param data
	 *            需要传送的内容。
	 * @throws TransferException
	 */
	public InputStream doPost(HashMap params, byte[] data) {
		URLConnection conn = null; // URL连结对象。
		BufferedReader in = null; // 请求后的返回信息的读取对象。
		String keyName = null;
		URL url = null;
		try {
			conn = url.openConnection();
			conn.setUseCaches(false);
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "multipart/form-data");

			// 设置参数
			if (params != null) {
				Set keys = params.keySet();
				// 遍历参数集取得参数名称和值
				if (!keys.isEmpty()) {
					Iterator iterator = keys.iterator();
					while (iterator.hasNext()) {
						keyName = (String) iterator.next();
						// 将参数加入到连接对象中
						conn.addRequestProperty(keyName, (String) params.get(keyName));
					}
				}
			}

			// 传输文件。
			DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(conn.getOutputStream()));
			dos.write(data);
			dos.flush();
			dos.close();
			return conn.getInputStream();
		} catch (FileNotFoundException fe) {
			InputStream err = ((HttpURLConnection) conn).getErrorStream();
//			if (err == null)
//				throw new TransferException("网络传输时发生的未知错误");
//			else
//				throw new TransferException("未知错误");
		} catch (IOException ioe) {
			ioe.printStackTrace();
//			throw new TransferException("网络传输错误！");
		}
		return null;
	}

	public static void main(String[] args) throws IOException {
		testPost();
	}
}
/*
import org.htmlparser.Node;  
import org.htmlparser.NodeFilter;  
import org.htmlparser.Parser;  
import org.htmlparser.filters.TagNameFilter;  
import org.htmlparser.tags.TableTag;  
import org.htmlparser.util.NodeList;  
public class TestHTMLParser {  
public static void testHtml() {     
     try {     
         String sCurrentLine;     
         String sTotalString;     
         sCurrentLine = "";     
         sTotalString = "";     
         java.io.InputStream l_urlStream;     
         java.net.URL l_url = new java.net.URL("http://www.ideagrace.com/html/doc/2006/07/04/00929.html");     
         java.net.HttpURLConnection l_connection = (java.net.HttpURLConnection) l_url.openConnection();     
         l_connection.connect();     
         l_urlStream = l_connection.getInputStream();     
         java.io.BufferedReader l_reader = new java.io.BufferedReader(new java.io.InputStreamReader(l_urlStream));     
         while ((sCurrentLine = l_reader.readLine()) != null) {     
           sTotalString += sCurrentLine+"/r/n";     
         //  System.out.println(sTotalString);     
         }     
         String testText = extractText(sTotalString);     
         System.out.println( testText );     
     
     } catch (Exception e) {     
         e.printStackTrace();     
     }     
     
   }     
       
   public static String extractText(String inputHtml) throws Exception {     
     StringBuffer text = new StringBuffer();     
     Parser parser = Parser.createParser(new String(inputHtml.getBytes(),"GBK"), "GBK");     
     // 遍历所有的节点     
     NodeList nodes = parser.extractAllNodesThatMatch(new NodeFilter() {     
         public boolean accept(Node node) {     
           return true;     
         }     
     });     
     
     System.out.println(nodes.size()); //打印节点的数量     
     for (int i=0;i<nodes.size();i++){     
          Node nodet = nodes.elementAt(i);     
          //System.out.println(nodet.getText());      
         text.append(new String(nodet.toPlainTextString().getBytes("GBK"))+"/r/n");               
     }     
     return text.toString();     
   }     
       
   public static void test5(String resource) throws Exception {     
     Parser myParser = new Parser(resource);     
     myParser.setEncoding("GBK");     
     String filterStr = "table";     
     NodeFilter filter = new TagNameFilter(filterStr);     
     NodeList nodeList = myParser.extractAllNodesThatMatch(filter);     
     TableTag tabletag = (TableTag) nodeList.elementAt(11);     
           
   }     
     
   public static void main(String[] args) throws Exception {     
     // test5("http://www.ggdig.com");     
     testHtml();     
   }     
}  
*/