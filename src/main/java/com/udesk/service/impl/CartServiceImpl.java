package com.udesk.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udesk.entity.Cart;
import com.udesk.entity.Goods;
import com.udesk.mapper.CartMapper;
import com.udesk.service.ICartService;
import com.udesk.service.IGoodsService;
import com.udesk.service.ex.InsertDataException;
import com.udesk.service.ex.UpdateDataException;

@Service("cartService")
public class CartServiceImpl implements ICartService {
	
	@Autowired private CartMapper cartMapper;
	@Autowired private IGoodsService goodsService;

	public void addToCart(Cart cart) {
		// ���жϹ��ﳵ�Ƿ��Ѿ������������
		Integer uid = cart.getUid();
		Long goodsId = cart.getGoodsId();
		Integer count = getCountByUidAndGoodsId(uid, goodsId);
		if (count == 0) { 
			// ���==0��û�����ݣ�������
			// ����insert()�������
			insert(cart);
		} else {
			// ���!=0�������ݣ����޸�����
			// -- ����changeGoodsNum()����޸�
			Integer num = cart.getGoodsNum();
			changeGoodsNum(num, uid, goodsId);
		}
	}

	public Integer getCountByUidAndGoodsId(Integer uid, Long goodsId) {
		return cartMapper.getCountByUidAndGoodsId(uid, goodsId);
	}

	public void changeGoodsNum(Integer num, Integer uid, Long goodsId) {
		Integer rows = cartMapper.changeGoodsNum(num, uid, goodsId);
		if (rows != 1) {
			throw new UpdateDataException("���빺�ﳵʧ�ܣ�");
		}
	}

	public List<Cart> getList(Integer uid, Integer page) {
		// ���page��Ч����Ϊpage=1
		if (page == null || page < 1) {
			page = 1;
		}
		// ���page�������ޣ���Ϊ���һҳ
		Integer dataCount = getCountByUid(uid);
		Integer maxPage = (int) Math.ceil(1. * dataCount / COUNT_PER_PAGE); // ����������ÿҳ������
		if (page > maxPage) {
			page = maxPage;
		}
		// ִ��
		Integer offset = (page - 1) * COUNT_PER_PAGE;
		Integer count = COUNT_PER_PAGE;
		return cartMapper.getList(uid, offset, count);
	}

	public Integer getCountByUid(Integer uid) {
		return cartMapper.getCountByUid(uid);
	}

	public Integer getMaxPage(Integer uid) {
		Integer dataCount = getCountByUid(uid);
		Integer maxPage = (int) Math.ceil(1. * dataCount / COUNT_PER_PAGE); // ����������ÿҳ������
		return maxPage;
	}

	public List<Cart> getListByIds(Integer uid, Integer[] ids) {
		return cartMapper.getListByIds(uid, ids);
	}
	
	/**
	 * �����ݱ��������µĹ��ﳵ����
	 * @param cart ���ﳵ����
	 * @return �����ӵĹ��ﳵ���ݣ���������id
	 */
	private Cart insert(Cart cart) {
		// ��ѯ��Ʒ����ϸ����
		Goods goods	= goodsService .getGoodsById(cart.getGoodsId());
		System.out.println("Service, goods=" + goods);
		// ��װ��Ҫ������
		cart.setGoodsImage(goods.getImage());
		cart.setGoodsPrice(goods.getPrice());
		cart.setGoodsTitle(goods.getTitle());
		// ִ�в�������
		Integer rows = cartMapper.insert(cart);
		// ��֤�������
		if (rows != 1) {
			throw new InsertDataException("���빺�ﳵʧ�ܣ�");
		}
		return cart;
	}

}









