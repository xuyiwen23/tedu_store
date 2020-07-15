package com.udesk.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.udesk.entity.Order;
import com.udesk.entity.Address;
import com.udesk.entity.Cart;
import com.udesk.entity.Goods;
import com.udesk.entity.OrderItem;
import com.udesk.mapper.OrderMapper;
import com.udesk.service.IAddressService;
import com.udesk.service.ICartService;
import com.udesk.service.IGoodsService;
import com.udesk.service.IOrderService;
import com.udesk.service.ex.InsertDataException;

public class OrderServiceImpl implements IOrderService {
	@Autowired
	OrderMapper orderMapper;
	
	@Autowired
	ICartService cartService;
	
	@Autowired
	IAddressService addressService;
	
	@Autowired
	IGoodsService goodsService;

	//��������Ʒһ�Զ࣬��Ҫ����
	@Transactional
	public Order createOrder(Integer uid, Integer addressId, Integer[] cartIds) {
		// ��ȡ��ǰʱ��
		Date now = new Date();
		// ���ݹ��ﳵ����id��ȡ���ﳵ����
		List<Cart> carts = cartService.getListByIds(uid, cartIds);
		// ������Ʒ���ܼ�
		Long totalPrice = 0L;
		for (Cart cart : carts) {
			totalPrice += cart.getGoodsPrice() * cart.getGoodsNum();
		}
		// �����ջ���ַid��ȡ�ջ���ַ����
		Address address = addressService.getAddressById(addressId);
		// ׼���������е�����
		Order order = new Order();
		order.setUid(uid);
		order.setRecvName(address.getRecvName());
		order.setRecvPhone(address.getRecvPhone());
		order.setRecvAddress(address.getRecvDistrict() + " " + address.getRecvAddress());
		order.setTotalPrice(totalPrice);
		order.setStatus(1); // 1:δ֧����2:��֧����3:����
		order.setCreateTime(now);
		order.setPayTime(null);
		// ִ���򶩵����в�������
		insertOrder(order);
		
		// ѭ���򶩵���Ʒ���в�������
		for (Cart cart : carts) {
			// ��ȡ���蹺�����Ʒ����Ϣ
			Goods goods = goodsService.getGoodsById(cart.getGoodsId());
			//TODO
			// �жϿ���Ƿ���㣬�ǣ������棬�������쳣��������������
			// ׼��������Ʒ������
			OrderItem orderItem = new OrderItem();
			orderItem.setOrderId(order.getId());
			orderItem.setGoodsId(cart.getGoodsId());
			orderItem.setGoodsTitle(goods.getTitle());
			orderItem.setGoodsImage(goods.getImage());
			orderItem.setGoodsPrice(goods.getPrice());
			orderItem.setGoodsNum(cart.getGoodsNum());
			// ִ���򶩵���Ʒ���в�������
			insertOrderItem(orderItem);
			// ɾ�����ﳵ�ж�Ӧ������
			//TODO
		}
		
		return order;
	}
	
	private Order insertOrder(Order order) {
		Integer rows = orderMapper.insertOrder(order);
		if (rows != 1) {
			throw new InsertDataException("�����������ݳ���δ֪��������ϵϵͳ����Ա��");
		}
		return order;
	}
	
	private OrderItem insertOrderItem(OrderItem orderItem) {
		Integer rows = orderMapper.insertOrderItem(orderItem);
		if (rows != 1) {
			throw new InsertDataException("����������Ʒ���ݳ���δ֪��������ϵϵͳ����Ա��");
		}
		return orderItem;
	}

}
