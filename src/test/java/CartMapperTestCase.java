import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.mapper.CartMapper;

public class CartMapperTestCase {

	private AbstractApplicationContext ac;
	private CartMapper cartMapper;
	
	@Test
	public void getListByIds() {
		Integer uid = 3;
		Integer[] ids = {10,14,15};
		List<Cart> carts = cartMapper.getListByIds(uid, ids);
		System.out.println("List:");
		for (Cart cart : carts) {
			System.out.println(cart);
		}
		System.out.println("END.");
	}
	
	@Test
	public void getList() {
		// (page - 1) * 3;
		// page:1, offset:0
		// page:2, offset:3
		// page:3, oofset:6
		Integer uid = 3;
		Integer offset = 6;
		Integer count = 3;
		List<Cart> carts
			= cartMapper.getList(uid, offset, count);
		System.out.println("List:");
		for (Cart cart : carts) {
			System.out.println(cart);
		}
		System.out.println("END");
	}
	
	@Test
	public void insert() {
		Cart cart = new Cart();
		cart.setUid(3);
		cart.setGoodsId(100L);
		cart.setGoodsNum(8);
		Integer rows = cartMapper.insert(cart );
		System.out.println("rows=" + rows);
		System.out.println(cart);
	}
	
	@Test
	public void getCountByUidAndGoodsId() {
		Integer uid = 30;
		Long goodsId = 100L;
		Integer count 
			= cartMapper.getCountByUidAndGoodsId(
					uid, goodsId);
		System.out.println("count=" + count);
	}
	
	@Test
	public void changeGoodsNum() {
		Integer num = -2;
		Integer uid = 3;
		Long goodsId = 100L;
		Integer rows
			= cartMapper.changeGoodsNum(
				num, uid, goodsId);
		System.out.println("rows=" + rows);
	}

	@Before
	public void doBefore() {
		ac = new ClassPathXmlApplicationContext(
				"spring-dao.xml");
		cartMapper = ac.getBean(
				"cartMapper", CartMapper.class);
	}

	@After
	public void doAfter() {
		ac.close();
	}

}
