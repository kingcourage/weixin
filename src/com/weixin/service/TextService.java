package com.weixin.service;

import java.util.List;
import java.util.Map;

import com.weixin.entity.Text;

public interface TextService {
	
	public List<Text> getText(Map map);
	public int insertText(String text);

}