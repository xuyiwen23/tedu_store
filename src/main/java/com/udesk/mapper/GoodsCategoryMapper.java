package com.udesk.mapper;

import java.util.List;

import com.udesk.entity.GoodsCategory;

public interface GoodsCategoryMapper {
	
	List<GoodsCategory> getListByParent(Long parentId);
}
