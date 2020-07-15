package com.udesk.service;

import com.udesk.entity.Order;

public interface IOrderService {
	
	/**
	 * ��������
	 * @param uid ��ǰ��¼���û�
	 * @param addressId ѡ����ջ���ַ��id
	 * @param cartIds ѡ�еĹ��ﳵ�е����ݵ�id
	 * @return �ɹ������Ķ���
	 */
	Order createOrder(Integer uid, Integer addressId, Integer[] cartIds);
}