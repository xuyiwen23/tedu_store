package com.udesk;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.udesk.entity.User;
import com.udesk.mapper.UserMapper;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class UserMapperTest {
	private ApplicationContext  ac;
    
	@Autowired
    UserMapper userMapper;

    @Test
    public void testCreateNewUser() {
        User user = new User();
        user.setGender(1);
        user.setUsername("Œ‰¥Û");
        user.setPassword("123456");
        user.setEmail("0123456789");
        userMapper.insert(user);
    }
    
    @Test
    public void getUserByUsername() {
        User user = userMapper.getUserByUsername("Œ‰¥Û");
        System.out.print(user);
    }

}
