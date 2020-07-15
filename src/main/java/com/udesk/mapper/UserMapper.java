package com.udesk.mapper;

import org.apache.ibatis.annotations.Param;

import com.udesk.entity.User;

public interface UserMapper {
	Integer insert(User user);

	User getUserByUsername(String username);
	
	User getUserById(Integer id);
	
	Integer changePassword(@Param("uid") Integer uid, @Param("password") String password);
	
	Integer changeInfo(User user);

}
