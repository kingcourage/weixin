package com.weixin.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import net.sf.json.JSONObject;

public class AccessTokenUtil implements Runnable{
	public static String access_token = null;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		access_token = getAccess_token();
		while(true){
		try{	
			if (null != access_token) {
				
				// 休眠7000秒
				Thread.sleep(7000*1000);
			} else {
				// 如果access_token为null，60秒后再获取
				Thread.sleep(10 * 1000);
			}
		} catch (InterruptedException e) {
			try {
				Thread.sleep(60 * 1000);
			} catch (InterruptedException e1) {	
				e1.printStackTrace();
			}
			}
		
		}
		
	}
	public String getAccess_token() {		
		StringBuffer action = new StringBuffer();
		action.append("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential")
				.append("&appid=wx11af04aaaad6fe3b").append("&secret=04a766df2510d31e4bde99cfbcd62d5d");
		URL url;
		try {
			url = new URL(action.toString());
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setRequestMethod("GET");
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setDoInput(true);
			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] buf = new byte[size];
			is.read(buf);
			String resp = new String(buf, "UTF-8");
			JSONObject jsonObject = JSONObject.fromObject(resp);
			//System.out.println("access_token:" + jsonObject.toString());
			Object object = jsonObject.get("access_token");

			if (object != null) {
				access_token = String.valueOf(object);
			}
			System.out.println(access_token);
			return access_token;

		} catch (MalformedURLException e) {
			e.printStackTrace();
			return access_token;

		} catch (IOException e) {
			e.printStackTrace();
			return access_token;

		}

	}
	
}
