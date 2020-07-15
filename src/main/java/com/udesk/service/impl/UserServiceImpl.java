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
			throw new UsernameConflictException("����ע����û���(" + result.getUsername()+ ")�Ѿ���ռ�ã�");
		}
	}

	public void insert(User user) throws InsertDataException {
	    // ��������
	    String salt = getRandomSalt();
		String md5Password = getEncrpytedPassword(user.getPassword(), salt);
		user.setSalt(salt);
		user.setPassword(md5Password);
		// Ϊ�û�û���ύ����������ֵ
		user.setStatus(1);
		user.setIsDelete(0);
		// �����û����ݵ���־
		Date now = new Date();
		user.setCreatedUser(user.getUsername());
		user.setCreatedTime(now);
		user.setModifiedUser(user.getUsername());
		user.setModifiedTime(now);
		// ִ��ע��
		Integer rows = userMapper.insert(user);
		// �ж�ִ�н��
		if (rows != 1) {
			throw new InsertDataException("ע��ʱ����δ֪��������ϵϵͳ����Ա��");
		}
	}


	public User login(String username, String password) throws UserNotFoundException, PasswordNotMatchException {
		User user = getUserByUsername(username);
		if(user == null){
			throw new UserNotFoundException("���Ե�¼���û���(" + username + ")�����ڣ�");			
		}
		String salt = user.getSalt();
		String result = getEncrpytedPassword(password, salt);
		String md5Password = user.getPassword(); 
		if(result.equals(md5Password)) {
			throw new PasswordNotMatchException("�������");			
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
			throw new UpdateDataException("�޸�����ʱ����δ֪��������ϵϵͳ����Ա��");
		}
	}

	public void changePasswordByOldPassword(Integer uid, String oldPassword, String newPassword)
			throws UserNotFoundException, PasswordNotMatchException, UpdateDataException {
		// ����id��ѯ�û���Ϣ
		User user = userMapper.getUserById(uid);
		// ����Ƿ��ѯ���û���Ϣ
		if (user != null) {
			// ���ڣ���ȡ���û�����ֵ
			String salt = user.getSalt();
			// ���û�������������
			String md5OldPassword = getEncrpytedPassword(oldPassword, salt);
			// �ж�ԭ�����Ƿ���ȷ
			if (user.getPassword().equals(md5OldPassword)) {
				// ��ȷ�������������
				String md5NewPassword = getEncrpytedPassword(newPassword, salt);
				// ִ���޸�����
				changePassword(uid, md5NewPassword);
			} else {
				// �����׳�PasswordNotMatchException
				throw new PasswordNotMatchException("ԭ���벻��ȷ��");
			}
		} else {
			// �����ڣ��׳�UserNotFoundException
			throw new UserNotFoundException("���Է��ʵ��û�(id=" + uid + ")���ݲ����ڣ�");
		}
		
	}
	
	/**
	 * ��ȡ�������ֵ
	 * @return �������ֵ
	 */
	private String getRandomSalt() {
		return UUID.randomUUID().toString().toUpperCase();
	}
	
	/**
	 * ��ȡ���ܺ������
	 * @param src ԭʼ����
	 * @param salt ��
	 * @return ���ܺ������
	 * @see #md5(String)
	 */
	private String getEncrpytedPassword(String src, String salt) {
	    // ��ԭ�������
	    String s1 = md5(src);
	    // ���μ���
	    String s2 = md5(salt);
	    // ��2�μ��ܽ��ƴ�ӣ��ټ���
	    String s3 = s1 + s2;
	    String result = md5(s3);
	    // �����Ͻ���ټ���5��
	    for (int i = 0; i < 5; i++) {
	        result = md5(result);
	    }
	    // ����
	    return result;
	}

	/**
	 * ʹ��MD5�㷨�����ݽ��м���
	 * @param src ԭ��
	 * @return ����
	 */
	private String md5(String src) {
	    return DigestUtils.md5DigestAsHex(src.getBytes()).toUpperCase();
	}

	public void changeInfo(User user) throws UpdateDataException,UserNotFoundException{
		// �ȼ��Ҫ�޸ĵ������Ƿ����
		User result = userMapper.getUserById(user.getId());
		if (result != null) {
			// �û����ڣ�׼����־
			user.setModifiedUser(user.getUsername() == null ? result.getUsername() : user.getUsername());
			user.setModifiedTime(new Date());
			// �޸���Ϣ������ȡ����ֵ
			Integer rows = userMapper.changeInfo(user);
			// �жϲ������
			if (rows != 1) {
				throw new UpdateDataException("�޸��û���Ϣʱ����δ֪��������ϵϵͳ����Ա��");
			}
		} else {
			// �û�������
			throw new UserNotFoundException("���Է��ʵ��û�����(id=" + user.getId() + ")�����ڣ�");
		}
		
	}

	public User getUserById(Integer id) throws UserNotFoundException{
		User user = userMapper.getUserById(id);
		if (user != null){
			return user;			
		}else{
			throw new UserNotFoundException("���Ե�¼���û�id(" + id + ")�����ڣ�");
		}
	}

}
