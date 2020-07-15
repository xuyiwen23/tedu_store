package cn.tedu.store.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.tedu.store.service.IOrderService;

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
		// ��ȡuid
		Integer uid = getUidFromSession(session);
		// ����ҵ������ķ�����������
		orderService.createOrder(uid, addressId, cartIds);
		// �����ɹ���ת�����ض���
		return null;
	}
	
}










