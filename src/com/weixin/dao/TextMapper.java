package com.weixin.dao;

import java.util.List;
import java.util.Map;

import com.weixin.entity.Text;

public interface TextMapper {
	
	public List<Text> selectText(Map map);
	public int insertText(String text);
}
