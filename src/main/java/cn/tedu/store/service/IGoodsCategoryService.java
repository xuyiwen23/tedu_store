package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.entity.GoodsCategory;

public interface IGoodsCategoryService {

	/**
	 * 根据父级分类，获取分类列表
	 * @param parentId 父级分类的id，如果需要获取一级分类，则父级分类为0
	 * @return 子级分类列表
	 */
	List<GoodsCategory> getListByParent(Long parentId);
	
}
