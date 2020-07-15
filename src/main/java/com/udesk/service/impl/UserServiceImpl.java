package com.udesk.service.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.udesk.entity.User;
import com.udesk.mapper.UserMapper;
import com.udesk.service.IUserService;
import com.udesk.service.ex.InsertDataException;
import com.udesk.service.ex.PasswordNotMatchException;
import com.udesk.service.ex.UpdateDataException;
import com.udesk.service.ex.UserNotFoundException;
import com.udesk.service.ex.UsernameConflictException;

@Service("userService")
public class UserServiceImpl implements IUserService {
	
	@Autowired 
	private UserMapper userMapper;

	public User register(User user) throws UsernameConflictException{
		User result = userMapper.getUserByUsername(user.getUsername());
		if (result == null){
			insert(user);
			return result;
		}else{
			throw new UsernameConflictException("尝试注册的用户名(" + result.getUsername()+ ")已经被占用！");
		}
	}

	public void insert(User user) throws InsertDataException {
	    // 加密密码
	    String salt = getRandomSalt();
		String md5Password = getEncrpytedPassword(user.getPassword(), salt);
		user.setSalt(salt);
		user.setPassword(md5Password);
		// 为用户没有提交的属性设置值
		user.setStatus(1);
		user.setIsDelete(0);
		// 设置用户数据的日志
		Date now = new Date();
		user.setCreatedUser(user.getUsername());
		user.setCreatedTime(now);
		user.setModifiedUser(user.getUsername());
		user.setModifiedTime(now);
		// 执行注册
		Integer rows = userMapper.insert(user);
		// 判断执行结果
		if (rows != 1) {
			throw new InsertDataException("注册时发生未知错误！请联系系统管理员！");
		}
	}


	public User login(String username, String password) throws UserNotFoundException, PasswordNotMatchException {
		User user = getUserByUsername(username);
		if(user == null){
			throw new UserNotFoundException("尝试登录的用户名(" + username + ")不存在！");			
		}
		String salt = user.getSalt();
		String result = getEncrpytedPassword(password, salt);
		String md5Password = user.getPassword(); 
		if(result.equals(md5Password)) {
			throw new PasswordNotMatchException("密码错误！");			
		}
		
		user.setPassword(null);
		user.setSalt(null);
		return user;
	}
	
	public User getUserByUsername(String username) {
		User user = userMapper.getUserByUsername(username);
		return user;
	}
	
	public void changePassword(Integer uid, String password) throws UpdateDataException {
		Integer rows = userMapper.changePassword(uid, password);
		if (rows != 1) {
			throw new UpdateDataException("修改密码时出现未知错误！请联系系统管理员！");
		}
	}

	public void changePasswordByOldPassword(Integer uid, String oldPassword, String newPassword)
			throws UserNotFoundException, PasswordNotMatchException, UpdateDataException {
		// 根据id查询用户信息
		User user = userMapper.getUserById(uid);
		// 检查是否查询到用户信息
		if (user != null) {
			// 存在：获取该用户的盐值
			String salt = user.getSalt();
			// 将用户输入的密码加密
			String md5OldPassword = getEncrpytedPassword(oldPassword, salt);
			// 判断原密码是否正确
			if (user.getPassword().equals(md5OldPassword)) {
				// 正确：将新密码加密
				String md5NewPassword = getEncrpytedPassword(newPassword, salt);
				// 执行修改密码
				changePassword(uid, md5NewPassword);
			} else {
				// 错误：抛出PasswordNotMatchException
				throw new PasswordNotMatchException("原密码不正确！");
			}
		} else {
			// 不存在：抛出UserNotFoundException
			throw new UserNotFoundException("尝试访问的用户(id=" + uid + ")数据不存在！");
		}
		
	}
	
	/**
	 * 获取随机的盐值
	 * @return 随机的盐值
	 */
	private String getRandomSalt() {
		return UUID.randomUUID().toString().toUpperCase();
	}
	
	/**
	 * 获取加密后的密码
	 * @param src 原始密码
	 * @param salt 盐
	 * @return 加密后的密码
	 * @see #md5(String)
	 */
	private String getEncrpytedPassword(String src, String salt) {
	    // 将原密码加密
	    String s1 = md5(src);
	    // 将盐加密
	    String s2 = md5(salt);
	    // 将2次加密结果拼接，再加密
	    String s3 = s1 + s2;
	    String result = md5(s3);
	    // 将以上结果再加密5次
	    for (int i = 0; i < 5; i++) {
	        result = md5(result);
	    }
	    // 返回
	    return result;
	}

	/**
	 * 使用MD5算法对数据进行加密
	 * @param src 原文
	 * @return 密文
	 */
	private String md5(String src) {
	    return DigestUtils.md5DigestAsHex(src.getBytes()).toUpperCase();
	}

	public void changeInfo(User user) throws UpdateDataException,UserNotFoundException{
		// 先检查要修改的数据是否存在
		User result = userMapper.getUserById(user.getId());
		if (result != null) {
			// 用户存在，准备日志
			user.setModifiedUser(user.getUsername() == null ? result.getUsername() : user.getUsername());
			user.setModifiedTime(new Date());
			// 修改信息，并获取返回值
			Integer rows = userMapper.changeInfo(user);
			// 判断操作结果
			if (rows != 1) {
				throw new UpdateDataException("修改用户信息时出现未知错误！请联系系统管理员！");
			}
		} else {
			// 用户不存在
			throw new UserNotFoundException("尝试访问的用户数据(id=" + user.getId() + ")不存在！");
		}
		
	}

	public User getUserById(Integer id) throws UserNotFoundException{
		User user = userMapper.getUserById(id);
		if (user != null){
			return user;			
		}else{
			throw new UserNotFoundException("尝试登录的用户id(" + id + ")不存在！");
		}
	}

}
