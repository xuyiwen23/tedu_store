package com.udesk.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udesk.entity.Goods;
import com.udesk.mapper.GoodsMapper;
import com.udesk.service.IGoodsService;

@Service("goodsService")
public class GoodsServiceImpl implements IGoodsService {

	@Autowired private GoodsMapper goodsMapper;
	
	public List<Goods> getHotList(Long categoryId, Integer count) {
		return goodsMapper.getListByCategory(categoryId, 0, count);
	}

	public Goods getGoodsById(Long id) {
		return goodsMapper.getGoodsById(id);
	}

}








