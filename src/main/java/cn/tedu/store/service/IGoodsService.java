package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.entity.Goods;

public interface IGoodsService {

	/**
	 * 获取热卖商品列表
	 * @param categoryId 商品分类的id
	 * @param count 获取的商品的数量
	 * @return 商品列表，如果没有匹配的数据，则返回无元素的集合
	 */
	List<Goods> getHotList(Long categoryId, Integer count);
	
	/**
	 * 根据商品id获取商品数据
	 * @param id 商品id
	 * @return 商品数据，如果没有匹配的数据，则返回null
	 */
	Goods getGoodsById(Long id);
}





