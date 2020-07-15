package cn.tedu.store.service;

import cn.tedu.store.entity.User;
import cn.tedu.store.service.ex.InsertDataException;
import cn.tedu.store.service.ex.PasswordNotMatchException;
import cn.tedu.store.service.ex.UpdateDataException;
import cn.tedu.store.service.ex.UserNotFoundException;
import cn.tedu.store.service.ex.UsernameConflictException;

public interface IUserService {

	/**
	 * �û�ע��
	 * 
	 * @param user
	 *            �û���Ϣ
	 * @return �ɹ�ע�������
	 * @throws UsernameConflictException
	 *             �û�����ռ��
	 * @throws InsertDataException
	 *             δ֪����
	 */
	User reg(User user) 
		throws UsernameConflictException, 
				InsertDataException;

	/**
	 * �û���¼
	 * @param username �û���
	 * @param password ����
	 * @return �ɹ���¼���û�����
	 * @throws UserNotFoundException �û���������
	 * @throws PasswordNotMatchException �������
	 */
	User login(String username, String password) 
		throws UserNotFoundException, 
				PasswordNotMatchException;
	
	/**
	 * ͨ����֤ԭ�������޸�����
	 * @param uid �û�id
	 * @param oldPassword ԭ����
	 * @param newPassword ������
	 * @throws UserNotFoundException ���Է��ʵ��û����ݲ�����
	 * @throws PasswordNotMatchException ԭ���벻��ȷ
	 * @throws UpdateDataException ����ʧ��
	 */
	void changePasswordByOldPassword(
		Integer uid, String oldPassword, String newPassword)
			throws UserNotFoundException,
				PasswordNotMatchException, 
					UpdateDataException;
	
	/**
	 * �����û�����
	 * 
	 * @param user
	 *            �û�����
	 */
	void insert(User user) throws InsertDataException;

	/**
	 * �����û�����ѯ�û�����
	 * 
	 * @param username
	 *            �û���
	 * @return ���û���ƥ����û����ݣ����û��ƥ������ݣ��򷵻�null
	 */
	User getUserByUsername(String username);
	
	/**
	 * �����û�����ѯ�û�����
	 * @param uid �û�id
	 * @return ���û�idƥ����û����ݣ����û��ƥ������ݣ��򷵻�null
	 */
	User getUserById(Integer uid);
	
	/**
	 * �޸�����
	 * @param uid �û�id
	 * @param password ������
	 * @throws #UpdateDataException
	 */ 
	void changePassword(Integer uid, String password)
		throws UpdateDataException;
	
	/**
	 * �޸ĸ�����Ϣ���������û������Ա�ͷ�񡢵绰������
	 * @param user ���޸ĵ��û�������Ϣ�����ٰ����û�id�����޸ĵ����ݰ������û������Ա�ͷ�񡢵绰������
	 */
	void changeInfo(User user);

}



