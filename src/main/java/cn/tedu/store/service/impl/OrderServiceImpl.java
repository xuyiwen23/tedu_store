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
		Address address 
			= addressService.getAddressById(addressId);
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
		}
		
		return order;
	}
	
	private Order insertOrder(Order order) {
		Integer rows = orderMapper.insertOrder(order);
		if (rows != 1) {
			throw new InsertDataException(
				"�����������ݳ���δ֪��������ϵϵͳ����Ա��");
		}
		return order;
	}
	
	private OrderItem insertOrderItem(OrderItem orderItem) {
		Integer rows = orderMapper.insertOrderItem(orderItem);
		if (rows != 1) {
			throw new InsertDataException(
				"����������Ʒ���ݳ���δ֪��������ϵϵͳ����Ա��");
		}
		return orderItem;
	}

}










