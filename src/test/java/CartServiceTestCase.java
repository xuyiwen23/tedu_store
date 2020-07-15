import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.service.ICartService;
import cn.tedu.store.service.ex.ServiceException;

public class CartServiceTestCase {

	private AbstractApplicationContext ac;
	private ICartService cartService;
	
	@Test
	public void getList() {
		Integer uid = 3;
		Integer page = 400000; // 1/2/3
		List<Cart> carts
			= cartService.getList(uid, page);
		System.out.println("List:");
		for (Cart cart : carts) {
			System.out.println(cart);
		}
		System.out.println("END");
	}
	
	@Test
	public void addToCart() {
		try {
			Cart cart = new Cart();
			cart.setUid(8);
			cart.setGoodsId(200L);
			cart.setGoodsNum(-1);
			cartService.addToCart(cart);
			System.out.println("OK");
		} catch(ServiceException e) {
			System.out.println(e.getMessage());
		}
	}

	@Before
	public void doBefore() {
		ac = new ClassPathXmlApplicationContext(
				"spring-dao.xml", "spring-service.xml");
		cartService = ac.getBean(
				"cartService", ICartService.class);
	}

	@After
	public void doAfter() {
		ac.close();
	}

}
