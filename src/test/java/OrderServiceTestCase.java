import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.tedu.store.service.IOrderService;

public class OrderServiceTestCase {

	private AbstractApplicationContext ac;
	private IOrderService orderService;
	
	@Test
	public void createOrder() {
		Integer uid = 3;
		Integer addressId = 2;
		Integer[] cartIds = { 10, 12, 15};
		orderService.createOrder(uid, addressId, cartIds);
		System.out.println("OK");
	}

	@Before
	public void doBefore() {
		ac = new ClassPathXmlApplicationContext(
			"spring-dao.xml", "spring-service.xml");
		orderService = ac.getBean(
			"orderService", IOrderService.class);
	}

	@After
	public void doAfter() {
		ac.close();
	}

}
