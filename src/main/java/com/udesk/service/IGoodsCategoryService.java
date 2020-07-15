package com.udesk.service;

import java.util.List;

import com.udesk.entity.GoodsCategory;


public interface IGoodsCategoryService {
	
	List<GoodsCategory> getListByParent(Long parentId);

}
