package cn.tedu.store.service;

import cn.tedu.store.entity.User;
import cn.tedu.store.service.ex.InsertDataException;
import cn.tedu.store.service.ex.PasswordNotMatchException;
import cn.tedu.store.service.ex.UpdateDataException;
import cn.tedu.store.service.ex.UserNotFoundException;
import cn.tedu.store.service.ex.UsernameConflictException;

public interface IUserService {

	/**
	 * 用户注册
	 * 
	 * @param user
	 *            用户信息
	 * @return 成功注册的数据
	 * @throws UsernameConflictException
	 *             用户名被占用
	 * @throws InsertDataException
	 *             未知错误
	 */
	User reg(User user) 
		throws UsernameConflictException, 
				InsertDataException;

	/**
	 * 用户登录
	 * @param username 用户名
	 * @param password 密码
	 * @return 成功登录的用户数据
	 * @throws UserNotFoundException 用户名不存在
	 * @throws PasswordNotMatchException 密码错误
	 */
	User login(String username, String password) 
		throws UserNotFoundException, 
				PasswordNotMatchException;
	
	/**
	 * 通过验证原密码来修改密码
	 * @param uid 用户id
	 * @param oldPassword 原密码
	 * @param newPassword 新密码
	 * @throws UserNotFoundException 尝试访问的用户数据不存在
	 * @throws PasswordNotMatchException 原密码不正确
	 * @throws UpdateDataException 更新失败
	 */
	void changePasswordByOldPassword(
		Integer uid, String oldPassword, String newPassword)
			throws UserNotFoundException,
				PasswordNotMatchException, 
					UpdateDataException;
	
	/**
	 * 插入用户数据
	 * 
	 * @param user
	 *            用户数据
	 */
	void insert(User user) throws InsertDataException;

	/**
	 * 根据用户名查询用户数据
	 * 
	 * @param username
	 *            用户名
	 * @return 与用户名匹配的用户数据，如果没有匹配的数据，则返回null
	 */
	User getUserByUsername(String username);
	
	/**
	 * 根据用户名查询用户数据
	 * @param uid 用户id
	 * @return 与用户id匹配的用户数据，如果没有匹配的数据，则返回null
	 */
	User getUserById(Integer uid);
	
	/**
	 * 修改密码
	 * @param uid 用户id
	 * @param password 新密码
	 * @throws #UpdateDataException
	 */ 
	void changePassword(Integer uid, String password)
		throws UpdateDataException;
	
	/**
	 * 修改个人信息，包括：用户名、性别、头像、电话、邮箱
	 * @param user 被修改的用户的新信息，至少包括用户id，可修改的数据包括：用户名、性别、头像、电话、邮箱
	 */
	void changeInfo(User user);

}



