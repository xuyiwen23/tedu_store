package com.udesk.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.udesk.service.IOrderService;

@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

	@Autowired
	private IOrderService orderService;
	
	@RequestMapping(value="/create.do", method=RequestMethod.POST)
	public String create(
		HttpSession session,
		@RequestParam("address_id") Integer addressId,
		@RequestParam("cart_id") Integer[] cartIds) {
		// 获取uid
		Integer uid = getUidFromSession(session);
		// 调用业务层对象的方法创建订单
		orderService.createOrder(uid, addressId, cartIds);
		// 创建成功后，转发或重定向
		return null;
	}
	
}










