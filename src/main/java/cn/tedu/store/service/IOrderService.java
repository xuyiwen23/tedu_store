package cn.tedu.store.service;

import cn.tedu.store.entity.Order;

public interface IOrderService {
	
	/**
	 * 创建订单
	 * @param uid 当前登录的用户
	 * @param addressId 选择的收货地址的id
	 * @param cartIds 选中的购物车中的数据的id
	 * @return 成功创建的订单
	 */
	Order createOrder(Integer uid, Integer addressId, Integer[] cartIds);
}
