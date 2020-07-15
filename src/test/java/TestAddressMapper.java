import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.tedu.store.entity.Address;
import cn.tedu.store.mapper.AddressMapper;

public class TestAddressMapper {
	
	private AbstractApplicationContext ac;
	private AddressMapper addressMapper;
	
	@Test
	public void insert() {
		Address address = new Address();
		address.setUid(1);
		address.setRecvName("¡ı¿œ ¶");
		Integer rows = addressMapper.insert(address);
		System.out.println("rows=" + rows);
	}
	
	@Before
	public void doBefore() {
		ac = new ClassPathXmlApplicationContext(
			"spring-dao.xml");
		addressMapper = ac.getBean(
			"addressMapper", AddressMapper.class);
	}
	
	@After
	public void doAfter() {
		ac.close();
	}

}
