package com.weixin.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SendTextUtil {	        	    
	    public  JSONArray  getOpenids(){
	        JSONArray array =null;
	        String urlstr ="https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
	        urlstr =urlstr.replace("ACCESS_TOKEN",  AccessTokenUtil.access_token);
	        urlstr =urlstr.replace("NEXT_OPENID", "");
	        URL url;
	        try {
	            url = new URL(urlstr);
	            HttpURLConnection http = (HttpURLConnection) url.openConnection();        
	            http.setRequestMethod("GET");        
	            http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");    
	            http.setDoInput(true);
	            InputStream is =http.getInputStream();
	            int size =is.available();
	            byte[] buf=new byte[size];
	            is.read(buf);
	            String resp =new String(buf,"UTF-8");
	            JSONObject jsonObject =JSONObject.fromObject(resp);
	            //System.out.println("resp:"+jsonObject.toString());
	            array =jsonObject.getJSONObject("data").getJSONArray("openid");
	            return array;
	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	             return array;
	             
	        } catch (IOException e) {
	            e.printStackTrace();
	             return array;
	         
	        }
	    }
	
	    public String sendTextByOpenids(String textMsg){
	        String urlstr ="https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN";
	        urlstr =urlstr.replace("ACCESS_TOKEN",  AccessTokenUtil.access_token);
	        System.out.println(AccessTokenUtil.access_token);
	        String reqjson =createGroupText(getOpenids(),textMsg);
	        String message = "fail";
	        try {
	             
	            URL httpclient =new URL(urlstr);
	            HttpURLConnection conn =(HttpURLConnection) httpclient.openConnection();
	            conn.setConnectTimeout(5000);
	            conn.setReadTimeout(2000);
	            conn.setRequestMethod("POST");
	            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");    
	            conn.setDoOutput(true);        
	            conn.setDoInput(true);
	            conn.connect();
	            OutputStream os= conn.getOutputStream();    
	           // System.out.println("req:"+reqjson);
	            os.write(reqjson.getBytes("UTF-8"));//传入参数    
	            os.flush();
	            os.close();
	            
	            InputStream is =conn.getInputStream();
	            int size =is.available();
	            byte[] jsonBytes =new byte[size];
	            is.read(jsonBytes);
	            message=new String(jsonBytes,"UTF-8");
	           // System.out.println("resp:"+message);
	            return message;
	         
	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	            return message;
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	            return message;
	        } 
	    }
	    
	    private String createGroupText(JSONArray array,String textMsg){
	         JSONObject gjson =new JSONObject();
	         gjson.put("touser", array);
	         gjson.put("msgtype", "text");	         
	         JSONObject text =new JSONObject();
	         text.put("content",textMsg);
	         //JSONObject image =new JSONObject();
	         //image.put("media_id","ReCXQUbb6kloUj8QYM5l9r2m8oxdt3K4RpkUkRlvcaZAssnA1cm4tsVS5QnyVky-");
	         gjson.put("text", text);
	         //gjson.put("image", image);
	        return gjson.toString();
	    }
}
