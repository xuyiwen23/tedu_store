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
		// �����û�����ѯ�û���Ϣ
		User result = getUserByUsername(
				user.getUsername());
		// �ж��Ƿ��ѯ������
		if (result == null) {
			// Ϊ�գ��û������ã�ִ��ע��
			insert(user);
			// ����
			return user;
		} else {
			// ��Ϊ�գ��û����Ѿ���ռ�ã��׳�UsernameConflictException
			throw new UsernameConflictException(
				"����ע����û���(" + user.getUsername() 
					+ ")�Ѿ���ռ�ã�");
		}
	}

	public User login(String username, String password) 
		throws UserNotFoundException, 
			PasswordNotMatchException {
		// �����û�����ѯ�û���Ϣ
		User user = getUserByUsername(username);
		// �ж��Ƿ��ѯ���û���Ϣ
		if (user != null) {
			// �ǣ��û�����ƥ������ݣ����û�����ȷ�����ȡ��ѯ����е���
			String salt = user.getSalt();
			// -- ���û����������ִ�м���
			String md5Password
				= getEncrpytedPassword(password, salt);
			// �ж����ϼ������������ݿ���Ƿ�ƥ��
			if (user.getPassword().equals(md5Password)) {
				// �ǣ���¼�ɹ������ز�ѯ���Ķ���
				user.setPassword(null);
				user.setSalt(null);
				return user;
			} else {
				// ����������׳��쳣��PasswordNotMatchException
				throw new PasswordNotMatchException(
					"�������");
			}
		} else {
			// ��û�����û���ƥ������ݣ����û��������׳��쳣��UserNotFoundException
			throw new UserNotFoundException(
				"���Ե�¼���û���(" + username + ")�����ڣ�");
		}
	}

	public void changePasswordByOldPassword(
		Integer uid, String oldPassword, String newPassword)
			throws UserNotFoundException, 
				PasswordNotMatchException, 
					UpdateDataException {
		// ����id��ѯ�û���Ϣ
		User user = getUserById(uid);
		// ����Ƿ��ѯ���û���Ϣ
		if (user != null) {
			// ���ڣ���ȡ���û�����ֵ
			String salt = user.getSalt();
			// ���û�������������
			String md5OldPassword 
				= getEncrpytedPassword(oldPassword, salt);
			// �ж�ԭ�����Ƿ���ȷ
			if (user.getPassword().equals(md5OldPassword)) {
				// ��ȷ�������������
				String md5NewPassword
					= getEncrpytedPassword(newPassword, salt);
				// ִ���޸�����
				changePassword(uid, md5NewPassword);
			} else {
				// �����׳�PasswordNotMatchException
				throw new PasswordNotMatchException("ԭ���벻��ȷ��");
			}
		} else {
			// �����ڣ��׳�UserNotFoundException
			throw new UserNotFoundException(
				"���Է��ʵ��û�(id=" + uid + ")���ݲ����ڣ�");
		}
	}
	
	public void insert(User user)
		throws InsertDataException {
		// ��������
		String salt = getRandomSalt();
		String md5Password
			= getEncrpytedPassword(
				user.getPassword(), salt);
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
			throw new InsertDataException(
				"ע��ʱ����δ֪��������ϵϵͳ����Ա��");
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
		// ���ó־ò�����ͬ������ʵ���޸Ĺ���
		Integer rows
			= userMapper.changePassword(uid, password);
		// �����Ǹ����û�id���޸�
		// ��ȷ����������£���Ӱ�������һ����1
		// ���ԣ������Ӱ����������1�����ӳ���������
		if (rows != 1) {
			throw new UpdateDataException(
				"�޸�����ʱ����δ֪��������ϵϵͳ����Ա��");
		}
	}

	public void changeInfo(User user) {
		// �ȼ��Ҫ�޸ĵ������Ƿ����
		User result = getUserById(user.getId());
		if (result != null) {
			// �û����ڣ�׼����־
			user.setModifiedUser(
				user.getUsername() == null ? 
					result.getUsername() : user.getUsername());
			user.setModifiedTime(new Date());
			// �޸���Ϣ������ȡ����ֵ
			Integer rows 
				= userMapper.changeInfo(user);
			// �жϲ������
			if (rows != 1) {
				throw new UpdateDataException(
					"�޸��û���Ϣʱ����δ֪��������ϵϵͳ����Ա��");
			}
		} else {
			// �û�������
			throw new UserNotFoundException(
				"���Է��ʵ��û�����(id=" + user.getId() + ")�����ڣ�");
		}
	}
	
	/**
	 * ��ȡ�������ֵ
	 * @return �������ֵ
	 */
	private String getRandomSalt() {
		return UUID.randomUUID()
				.toString().toUpperCase();
	}
	
	/**
	 * ��ȡ���ܺ������
	 * @param src ԭʼ����
	 * @param salt ��
	 * @return ���ܺ������
	 * @see #md5(String)
	 */
	private String getEncrpytedPassword(
			String src, String salt) {
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
		return DigestUtils
				.md5DigestAsHex(src.getBytes())
					.toUpperCase();
	}

}













