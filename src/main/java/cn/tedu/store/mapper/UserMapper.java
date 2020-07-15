package cn.tedu.store.mapper;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.User;

public interface UserMapper {

	/**
	 * 插入用户数据
	 * 
	 * @param user
	 *            用户数据
	 * @return 受影响的行数
	 */
	Integer insert(User user);

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
	 * @return 受影响的行数
	 */ 
	Integer changePassword(
		    @Param("uid") Integer uid, 
		    @Param("password") String password);
	
	/**
	 * 修改个人信息，包括：用户名、性别、头像、电话、邮箱
	 * @param user 被修改的用户的新信息，至少包括用户id，可修改的数据包括：用户名、性别、头像、电话、邮箱
	 * @return 受影响的行数
	 */
	Integer changeInfo(User user);
	
}








