package com.udesk.service;

import com.udesk.entity.User;
import com.udesk.service.ex.InsertDataException;
import com.udesk.service.ex.PasswordNotMatchException;
import com.udesk.service.ex.UpdateDataException;
import com.udesk.service.ex.UserNotFoundException;
import com.udesk.service.ex.UsernameConflictException;

public interface IUserService {
	User register(User user) throws UsernameConflictException;

	void insert(User user) throws InsertDataException;

	User getUserByUsername(String username);
	
	User getUserById(Integer id);
	
	User login(String username, String password) throws UserNotFoundException, PasswordNotMatchException;
	
	void changePassword(Integer uid, String password)throws UpdateDataException;
		
	void changePasswordByOldPassword(Integer uid, String oldPassword, String newPassword) throws UserNotFoundException,PasswordNotMatchException,UpdateDataException;
 
	void changeInfo(User user);
}
