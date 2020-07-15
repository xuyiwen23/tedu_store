import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.tedu.store.entity.Goods;
import cn.tedu.store.service.IGoodsService;

public class GoodsServiceTestCase {

	private AbstractApplicationContext ac;
	private IGoodsService goodsService;
	
	@Test
	public void getListByParent() {
		Long categoryId = 163L;
		Integer count = 3;
		List<Goods> goodsList
			= goodsService.getHotList(categoryId, count);
		
		System.out.println("List:");
		for (Goods goods : goodsList) {
			System.out.println(goods);
		}
	}
	
	@Test
	public void getGoodsById() {
		Long id = 10000017L;
		Goods goods
			= goodsService.getGoodsById(id);
		System.out.println(goods);
	}

	@Before
	public void doBefore() {
		ac = new ClassPathXmlApplicationContext(
			"spring-dao.xml", "spring-service.xml");
		goodsService = ac.getBean(
			"goodsService", IGoodsService.class);
	}
	
	@After
	public void doAfter() {
		ac.close();
	}
}
