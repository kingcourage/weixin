package com.weixin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.weixin.entity.Text;
import com.weixin.service.TextService;
import com.weixin.util.AccessTokenUtil;
import com.weixin.util.SendTextUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/textController")
public class TextController {
	

	@Resource
	TextService textService;
	
	@RequestMapping("/getAllText")
	@ResponseBody
	public Map getAllText(){
		//启动获取AccessToken的线程
		AccessTokenUtil atu = new AccessTokenUtil();	
		Thread t = new Thread(atu);
		t.start();	
		Map map = new HashMap();
		//System.out.println(textService.getText(map));
		map.put("rows", textService.getText(map));
		return map;
	}	
	@RequestMapping("/sendText")
	@ResponseBody
	public Map sendText(String text){
		Map map = new HashMap();
		SendTextUtil stu = new SendTextUtil();
		String msg = stu.sendTextByOpenids(text);
		JSONObject jsonObject =JSONObject.fromObject(msg);	
		System.out.println(msg);
		map.put("msg",jsonObject.getString("errmsg"));
		return map;
	}
	
	@RequestMapping("/addText")
	@ResponseBody
	public Map addText(String text){
		Map map = new HashMap();
		int i = textService.insertText(text);
		if(i>0){
			map.put("msg","success");
		}else{
			map.put("msg","fail");
		}
		return map;
	}
}
