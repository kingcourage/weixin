package com.weixin.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.weixin.dao.TextMapper;
import com.weixin.entity.Text;
import com.weixin.service.TextService;

@Service("textService")
public class TextServiceImpl implements TextService {
	@Resource
	private TextMapper textMapper;
	@Override
	public List<Text> getText(Map map) {
		// TODO Auto-generated method stub
		return textMapper.selectText(map);
	}
	@Override
	public int insertText(String text) {
		// TODO Auto-generated method stub
		return textMapper.insertText(text);
	}
}
