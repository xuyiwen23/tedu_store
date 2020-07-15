package com.udesk.service;

import java.util.List;

import com.udesk.entity.Goods;

public interface IGoodsService {

	/**
	 * ��ȡ������Ʒ�б�
	 * @param categoryId ��Ʒ�����id
	 * @param count ��ȡ����Ʒ������
	 * @return ��Ʒ�б������û��ƥ������ݣ��򷵻���Ԫ�صļ���
	 */
	List<Goods> getHotList(Long categoryId, Integer count);
	
	/**
	 * ������Ʒid��ȡ��Ʒ����
	 * @param id ��Ʒid
	 * @return ��Ʒ���ݣ����û��ƥ������ݣ��򷵻�null
	 */
	Goods getGoodsById(Long id);
}




