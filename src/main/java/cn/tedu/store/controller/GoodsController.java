package cn.tedu.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.store.entity.Goods;
import cn.tedu.store.entity.ResponseResult;
import cn.tedu.store.service.IGoodsService;

@Controller
@RequestMapping("/goods")
public class GoodsController extends BaseController {

	@Autowired
	private IGoodsService goodsService;
	
	@RequestMapping("/hot_list.do")
	@ResponseBody
	public ResponseResult<List<Goods>> getHotList(
		@RequestParam("category_id") Long categoryId) {
		// ��������ֵ����
		ResponseResult<List<Goods>> rr
			= new ResponseResult<List<Goods>>();
		// ����ҵ�������getHotList(categoryId, 3)����������ȡ����ֵ
		List<Goods> goodsList
			= goodsService.getHotList(categoryId, 3);
		// �򷵻�ֵ�����data���Է�װ����
		rr.setData(goodsList);
		// ִ�з���
		return rr;
	}
	
	@RequestMapping("/details.do")
	@ResponseBody
	public ResponseResult<Goods> getDetails(
		@RequestParam("id") Long id) {
		// ��������ֵ����
		ResponseResult<Goods> rr
			= new ResponseResult<Goods>();
		// ����ҵ�������getGoodsById(id)����������ȡ����ֵ
		Goods goods = goodsService.getGoodsById(id);
		// �򷵻�ֵ�����data���Է�װ����
		rr.setData(goods);
		// ִ�з���
		return rr;
	}
	
}









