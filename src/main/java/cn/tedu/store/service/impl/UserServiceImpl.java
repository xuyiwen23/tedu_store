package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.tedu.store.entity.User;
import cn.tedu.store.mapper.UserMapper;
import cn.tedu.store.service.IUserService;
import cn.tedu.store.service.ex.InsertDataException;
import cn.tedu.store.service.ex.PasswordNotMatchException;
import cn.tedu.store.service.ex.UpdateDataException;
import cn.tedu.store.service.ex.UserNotFoundException;
import cn.tedu.store.service.ex.UsernameConflictException;

@Service("userService")
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private UserMapper userMapper;

	public User reg(User user) 
		throws UsernameConflictException,
			InsertDataException {
		// 根据用户名查询用户信息
		User result = getUserByUsername(
				user.getUsername());
		// 判断是否查询到数据
		if (result == null) {
			// 为空：用户名可用，执行注册
			insert(user);
			// 返回
			return user;
		} else {
			// 不为空：用户名已经被占用，抛出UsernameConflictException
			throw new UsernameConflictException(
				"尝试注册的用户名(" + user.getUsername() 
					+ ")已经被占用！");
		}
	}

	public User login(String username, String password) 
		throws UserNotFoundException, 
			PasswordNotMatchException {
		// 根据用户名查询用户信息
		User user = getUserByUsername(username);
		// 判断是否查询到用户信息
		if (user != null) {
			// 是：用户名有匹配的数据，即用户名正确，则获取查询结果中的盐
			String salt = user.getSalt();
			// -- 对用户输入的密码执行加密
			String md5Password
				= getEncrpytedPassword(password, salt);
			// 判断以上加密密码与数据库的是否匹配
			if (user.getPassword().equals(md5Password)) {
				// 是：登录成功，返回查询到的对象
				user.setPassword(null);
				user.setSalt(null);
				return user;
			} else {
				// 否：密码错误，抛出异常：PasswordNotMatchException
				throw new PasswordNotMatchException(
					"密码错误！");
			}
		} else {
			// 否：没有与用户名匹配的数据，即用户名错误，抛出异常：UserNotFoundException
			throw new UserNotFoundException(
				"尝试登录的用户名(" + username + ")不存在！");
		}
	}

	public void changePasswordByOldPassword(
		Integer uid, String oldPassword, String newPassword)
			throws UserNotFoundException, 
				PasswordNotMatchException, 
					UpdateDataException {
		// 根据id查询用户信息
		User user = getUserById(uid);
		// 检查是否查询到用户信息
		if (user != null) {
			// 存在：获取该用户的盐值
			String salt = user.getSalt();
			// 将用户输入的密码加密
			String md5OldPassword 
				= getEncrpytedPassword(oldPassword, salt);
			// 判断原密码是否正确
			if (user.getPassword().equals(md5OldPassword)) {
				// 正确：将新密码加密
				String md5NewPassword
					= getEncrpytedPassword(newPassword, salt);
				// 执行修改密码
				changePassword(uid, md5NewPassword);
			} else {
				// 错误：抛出PasswordNotMatchException
				throw new PasswordNotMatchException("原密码不正确！");
			}
		} else {
			// 不存在：抛出UserNotFoundException
			throw new UserNotFoundException(
				"尝试访问的用户(id=" + uid + ")数据不存在！");
		}
	}
	
	public void insert(User user)
		throws InsertDataException {
		// 加密密码
		String salt = getRandomSalt();
		String md5Password
			= getEncrpytedPassword(
				user.getPassword(), salt);
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
			throw new InsertDataException(
				"注册时发生未知错误！请联系系统管理员！");
		}
	}

	public User getUserByUsername(String username) {
		return userMapper.getUserByUsername(username);
	}

	public User getUserById(Integer uid) {
		return userMapper.getUserById(uid);
	}

	public void changePassword(
			Integer uid, String password)
				throws UpdateDataException {
		// 调用持久层对象的同名方法实现修改功能
		Integer rows
			= userMapper.changePassword(uid, password);
		// 由于是根据用户id来修改
		// 正确操作的情况下，受影响的行数一定是1
		// 所以，如果受影响行数不是1，则视出操作出错
		if (rows != 1) {
			throw new UpdateDataException(
				"修改密码时出现未知错误！请联系系统管理员！");
		}
	}

	public void changeInfo(User user) {
		// 先检查要修改的数据是否存在
		User result = getUserById(user.getId());
		if (result != null) {
			// 用户存在，准备日志
			user.setModifiedUser(
				user.getUsername() == null ? 
					result.getUsername() : user.getUsername());
			user.setModifiedTime(new Date());
			// 修改信息，并获取返回值
			Integer rows 
				= userMapper.changeInfo(user);
			// 判断操作结果
			if (rows != 1) {
				throw new UpdateDataException(
					"修改用户信息时出现未知错误！请联系系统管理员！");
			}
		} else {
			// 用户不存在
			throw new UserNotFoundException(
				"尝试访问的用户数据(id=" + user.getId() + ")不存在！");
		}
	}
	
	/**
	 * 获取随机的盐值
	 * @return 随机的盐值
	 */
	private String getRandomSalt() {
		return UUID.randomUUID()
				.toString().toUpperCase();
	}
	
	/**
	 * 获取加密后的密码
	 * @param src 原始密码
	 * @param salt 盐
	 * @return 加密后的密码
	 * @see #md5(String)
	 */
	private String getEncrpytedPassword(
			String src, String salt) {
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
		return DigestUtils
				.md5DigestAsHex(src.getBytes())
					.toUpperCase();
	}

}













