package cn.tedu.store.mapper;

import java.util.List;

import cn.tedu.store.entity.Order;
import cn.tedu.store.entity.OrderItem;
import cn.tedu.store.vo.OrderVO;

public interface OrderMapper {
	
	/**
	 * ���붩������
	 * @param order ��������
	 * @return ��Ӱ�������
	 */
	Integer insertOrder(Order order);
	
	/**
	 * ���붩����Ʒ����
	 * @param orderItem ������Ʒ����
	 * @return ��Ӱ�������
	 */
	Integer insertOrderItem(OrderItem orderItem);
	
	List<OrderVO> getOrder();
}







