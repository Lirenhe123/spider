package priv.lirenhe.test;

import java.util.HashMap;
import java.util.Map;

import priv.lirenhe.spy.Spy;
import priv.lirenhe.thread.SpyThread;

public class test01 {
	public static void main(String[] args) {
		Map<String,String> urls=new HashMap<String,String>();
		urls.put("01","http://127.0.0.1:14966/?iw-apikey=123&iw-cmd=QX_00980_1_List1&iw_ir_2=n_newslist_zb_item.aspx&ILWHBNjF4ckRKalAUGeb6AILWHBNjF4ckRKalAUGeb6A=%3d&__EVENTTARGET=&__EVENTARGUMENT=&__VIEWSTATE=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");
//		urls.put("02","http://hqc.hunnu.edu.cn/Home/hqgg/7274635419");
		
		for(Map.Entry<String, String>url:urls.entrySet()){
			Spy spy=new Spy(url.getValue());
			SpyThread thread=new SpyThread(spy);
			thread.setName(url.getKey());
			thread.start();
		}
	}
}
