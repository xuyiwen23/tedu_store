import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.tedu.store.mapper.OrderMapper;
import cn.tedu.store.vo.OrderVO;

public class OrderMapperTestCase {

	private AbstractApplicationContext ac;
	private OrderMapper orderMapper;
	
	@Test
	public void getList() {
		List<OrderVO> orders = orderMapper.getOrder();
		System.out.println("List:");
		for (OrderVO orderVO : orders) {
			System.out.println(orderVO);
		}
		System.out.println("END");
	}

	@Before
	public void doBefore() {
		ac = new ClassPathXmlApplicationContext(
				"spring-dao.xml");
		orderMapper = ac.getBean(
				"orderMapper", OrderMapper.class);
	}

	@After
	public void doAfter() {
		ac.close();
	}

}
