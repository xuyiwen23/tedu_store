package cn.tedu.store.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.entity.ResponseResult;
import cn.tedu.store.service.ICartService;

@Controller
@RequestMapping("/cart")
public class CartController extends BaseController {
	
	@Autowired
	private ICartService cartService;
	
	@RequestMapping("/add_to_cart.do")
	@ResponseBody
	public ResponseResult<Void> addToCart(
		HttpSession session,
		@RequestParam("goods_id") Long goodsId,
		@RequestParam("goods_num") Integer goodsNum) {
		Integer uid = getUidFromSession(session);
		Cart cart = new Cart();
		
		System.out.println("cartService=" + cartService);
		System.out.println("cart=" + cart);
		
		cart.setUid(uid);
		cart.setGoodsId(goodsId);
		cart.setGoodsNum(goodsNum);
		cartService.addToCart(cart);
		return new ResponseResult<Void>();
	}
	
	@RequestMapping("/list.do")
	@ResponseBody
	public ResponseResult<List<Cart>> getList(
		HttpSession session,
		@RequestParam(value="page", required=false, defaultValue="1") Integer page) {
		Integer uid = getUidFromSession(session);
		List<Cart> carts = cartService.getList(uid, page);
		ResponseResult<List<Cart>> rr
			= new ResponseResult<List<Cart>>();
		rr.setData(carts);
		return rr;
	}
	
	@RequestMapping("/get_max_page.do")
	@ResponseBody
	public ResponseResult<Integer> getMaxPage(
			HttpSession session) {
		Integer uid = getUidFromSession(session);
		Integer maxPage = cartService.getMaxPage(uid);
		ResponseResult<Integer> rr
			= new ResponseResult<Integer>();
		rr.setData(maxPage);
		return rr;
	}
	
	@RequestMapping("/get_list_by_ids.do")
	@ResponseBody
	public ResponseResult<List<Cart>> getListByIds(
		HttpSession session,
		@RequestParam("ids") Integer[] ids) {
		Integer uid = getUidFromSession(session);
		List<Cart> carts
			= cartService.getListByIds(uid, ids);
		ResponseResult<List<Cart>> rr
			= new ResponseResult<List<Cart>>();
		rr.setData(carts);
		return rr;
	}

}










