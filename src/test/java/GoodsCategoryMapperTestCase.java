import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.tedu.store.entity.GoodsCategory;
import cn.tedu.store.mapper.GoodsCategoryMapper;

public class GoodsCategoryMapperTestCase {

	private AbstractApplicationContext ac;
	private GoodsCategoryMapper goodsCategoryMapper;
	
	@Test
	public void getListByParent() {
		Long parentId = 1620000L;
		List<GoodsCategory> categories = 
				goodsCategoryMapper.getListByParent(parentId);
		System.out.println("List:");
		for (GoodsCategory goodsCategory : categories) {
			System.out.println(goodsCategory);
		}
	}

	@Before
	public void doBefore() {
		ac = new ClassPathXmlApplicationContext(
			"spring-dao.xml");
		goodsCategoryMapper = ac.getBean(
			"goodsCategoryMapper", 
			GoodsCategoryMapper.class);
	}
	
	@After
	public void doAfter() {
		ac.close();
	}
}
