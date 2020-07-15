import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.tedu.store.entity.Goods;
import cn.tedu.store.mapper.GoodsMapper;

public class GoodsMapperTestCase {

	private AbstractApplicationContext ac;
	private GoodsMapper goodsMapper;
	
	@Test
	public void getListByParent() {
		Long categoryId = 163L;
		Integer offset = 0;
		Integer count = 3;
		List<Goods> goodsList
			= goodsMapper.getListByCategory(
				categoryId, offset, count);
		
		System.out.println("List:");
		for (Goods goods : goodsList) {
			System.out.println(goods);
		}
	}

	@Before
	public void doBefore() {
		ac = new ClassPathXmlApplicationContext(
			"spring-dao.xml");
		goodsMapper = ac.getBean(
			"goodsMapper", GoodsMapper.class);
	}
	
	@After
	public void doAfter() {
		ac.close();
	}
}
