import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.tedu.store.entity.User;
import cn.tedu.store.mapper.UserMapper;

public class TestUserMapper {
	
	private AbstractApplicationContext ac;
	private UserMapper userMapper;
	
	@Before
	public void doBefore() {
		ac = new ClassPathXmlApplicationContext(
			"spring-dao.xml");
		userMapper = ac.getBean(
			"userMapper", UserMapper.class);
	}
	
	@After
	public void doAfter() {
		ac.close();
	}
	
	@Test
	public void insert() {
		User user = new User();
		user.setUsername("admin");
		user.setPassword("123456");
		user.setIsDelete(0);
		Integer rows = userMapper.insert(user);
		System.out.println("rows=" + rows);
	}

	@Test
	public void getUserByUsername() {
		String username = "admin";
		User user = userMapper.getUserByUsername(
				username);
		System.out.println("user=" + user);
	}
	
	@Test
	public void getUserById() {
		Integer uid = 3;
		User user = userMapper.getUserById(uid);
		System.out.println("user=" + user);
		// 0212D4E1B75DA01D6D18A48055079396
	}
	
	@Test
	public void changePassword() {
		Integer uid = 3;
		String password = "0212D4E1B75DA01D6D18A48055079396";
		Integer rows = userMapper
				.changePassword(uid, password);
		System.out.println("rows=" + rows);
	}
	
	@Test
	public void changeInfo() {
		User user = new User();
		user.setId(1);
		user.setUsername("SpringMVC");
		user.setGender(0);
		user.setAvatar("image");
		user.setPhone("13700137001");
		user.setEmail("springmvc@tedu.cn");
		user.setModifiedUser("SpringMVC");
		user.setModifiedTime(new Date());
		
		Integer rows = userMapper
				.changeInfo(user);
		System.out.println("rows=" + rows);
	}
	
}
