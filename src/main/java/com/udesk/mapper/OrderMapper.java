package com.udesk.mapper;

import com.udesk.entity.Order;
import com.udesk.entity.OrderItem;

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

}
