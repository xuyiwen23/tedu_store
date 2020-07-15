import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.tedu.store.entity.Address;
import cn.tedu.store.service.IAddressService;
import cn.tedu.store.service.ex.ServiceException;

public class TestAddressService {
	
	private AbstractApplicationContext ac;
	private IAddressService addressService;
	
	@Test
	public void deleteById() {
		Integer uid = 3;
		Integer id = 15;
		try {
			addressService.deleteById(uid, id);
			System.out.println("OK");
		} catch(ServiceException e) {
			System.out.println(e.getClass().getName());
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void setDefaultAddress() {
		Integer uid = 3;
		Integer id = 14;
		addressService.setDefaultAddress(uid, id);
		System.out.println("OK");
	}
	
	@Test
	public void getCountByUid() {
		Integer count
			= addressService.getCountByUid(3);
		System.out.println("count=" + count);
	}
	
	
	@Test
	public void getList() {
		List<Address> addresses
		= addressService.getList(3);
		System.out.println("getList() > start.");
		for (Address address : addresses) {
			System.out.println(address);
		}
		System.out.println("getList() > end.");
	}
	
	@Before
	public void doBefore() {
		ac = new ClassPathXmlApplicationContext(
			"spring-dao.xml", "spring-service.xml");
		addressService
			= ac.getBean("addressService", IAddressService.class);
	}
	
	@After
	public void doAfter() {
		ac.close();
	}

}
