import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.tedu.store.entity.User;
import cn.tedu.store.service.IUserService;
import cn.tedu.store.service.ex.InsertDataException;
import cn.tedu.store.service.ex.PasswordNotMatchException;
import cn.tedu.store.service.ex.ServiceException;
import cn.tedu.store.service.ex.UserNotFoundException;
import cn.tedu.store.service.ex.UsernameConflictException;

public class TestUserService {
	
	private AbstractApplicationContext ac;
	private IUserService userService;
	
	@Before
	public void doBefore() {
		ac = new ClassPathXmlApplicationContext(
			"spring-dao.xml", "spring-service.xml");
		userService
			= ac.getBean("userService", IUserService.class);
	}
	
	@After
	public void doAfter() {
		ac.close();
	}

	@Test
	public void reg() {
		try {
			User user = new User();
			user.setUsername("spring");
			user.setPassword("123456");
			User result = userService.reg(user);
			System.out.println("result=" + result);
		} catch (UsernameConflictException e) {
			System.out.println(e.getMessage());
		} catch (InsertDataException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void login() {
		try {
			String username = "admin";
			String password = "888888";
			User user 
				= userService.login(
					username, password);
			System.out.println("登录成功！" + user);
		} catch (UserNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (PasswordNotMatchException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void changePasswordByOldPassword() {
		try {
			Integer uid = 3;
			String oldPassword = "888888";
			String newPassword = "123456";
			userService.changePasswordByOldPassword(uid, oldPassword, newPassword);
			System.out.println("修改成功！");
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
		}
	}
	
}














