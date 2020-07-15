package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.store.entity.Address;
import cn.tedu.store.entity.Cart;
import cn.tedu.store.entity.Goods;
import cn.tedu.store.entity.Order;
import cn.tedu.store.entity.OrderItem;
import cn.tedu.store.mapper.OrderMapper;
import cn.tedu.store.service.IAddressService;
import cn.tedu.store.service.ICartService;
import cn.tedu.store.service.IGoodsService;
import cn.tedu.store.service.IOrderService;
import cn.tedu.store.service.ex.InsertDataException;

@Service("orderService")
public class OrderServiceImpl implements IOrderService {

	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private IAddressService addressService;
	@Autowired
	private ICartService cartService;
	@Autowired
	private IGoodsService goodsService;
	
	@Transactional
	public Order createOrder(
		Integer uid, Integer addressId, Integer[] cartIds) {
		// 获取当前时间
		Date now = new Date();
		// 根据购物车数据id获取购物车数据
		List<Cart> carts = cartService.getListByIds(uid, cartIds);
		// 计算商品的总价
		Long totalPrice = 0L;
		for (Cart cart : carts) {
			totalPrice += cart.getGoodsPrice() * cart.getGoodsNum();
		}
		// 根据收货地址id获取收货地址数据
		Address address 
			= addressService.getAddressById(addressId);
		// 准备订单表中的数据
		Order order = new Order();
		order.setUid(uid);
		order.setRecvName(address.getRecvName());
		order.setRecvPhone(address.getRecvPhone());
		order.setRecvAddress(address.getRecvDistrict() + " " + address.getRecvAddress());
		order.setTotalPrice(totalPrice);
		order.setStatus(1); // 1:未支付，2:已支付，3:……
		order.setCreateTime(now);
		order.setPayTime(null);
		// 执行向订单表中插入数据
		insertOrder(order);
		
		// 循环向订单商品表中插入数据
		for (Cart cart : carts) {
			// 获取所需购买的商品的信息
			Goods goods = goodsService.getGoodsById(cart.getGoodsId());
			// 判断库存是否充足，是，则减库存，否，则抛异常，不允许创建订单
			// 准备订单商品表数据
			OrderItem orderItem = new OrderItem();
			orderItem.setOrderId(order.getId());
			orderItem.setGoodsId(cart.getGoodsId());
			orderItem.setGoodsTitle(goods.getTitle());
			orderItem.setGoodsImage(goods.getImage());
			orderItem.setGoodsPrice(goods.getPrice());
			orderItem.setGoodsNum(cart.getGoodsNum());
			// 执行向订单商品表中插入数据
			insertOrderItem(orderItem);
			// 删除购物车中对应的数据
		}
		
		return order;
	}
	
	private Order insertOrder(Order order) {
		Integer rows = orderMapper.insertOrder(order);
		if (rows != 1) {
			throw new InsertDataException(
				"创建订单数据出现未知错误！请联系系统管理员！");
		}
		return order;
	}
	
	private OrderItem insertOrderItem(OrderItem orderItem) {
		Integer rows = orderMapper.insertOrderItem(orderItem);
		if (rows != 1) {
			throw new InsertDataException(
				"创建订单商品数据出现未知错误！请联系系统管理员！");
		}
		return orderItem;
	}

}










