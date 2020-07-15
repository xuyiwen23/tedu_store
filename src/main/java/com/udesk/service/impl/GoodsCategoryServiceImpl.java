package com.udesk.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udesk.service.IGoodsCategoryService;
import com.udesk.entity.GoodsCategory;
import com.udesk.mapper.GoodsCategoryMapper;

@Service("goodsCategoryService")
public class GoodsCategoryServiceImpl implements IGoodsCategoryService {
	
	@Autowired private 
	GoodsCategoryMapper goodsCategoryMapper;

	public List<GoodsCategory> getListByParent(Long parentId) {
		return goodsCategoryMapper.getListByParent(parentId);
	}
	

}
