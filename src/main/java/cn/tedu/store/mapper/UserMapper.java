package cn.tedu.store.mapper;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.User;

public interface UserMapper {

	/**
	 * �����û�����
	 * 
	 * @param user
	 *            �û�����
	 * @return ��Ӱ�������
	 */
	Integer insert(User user);

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
	 * @return ��Ӱ�������
	 */ 
	Integer changePassword(
		    @Param("uid") Integer uid, 
		    @Param("password") String password);
	
	/**
	 * �޸ĸ�����Ϣ���������û������Ա�ͷ�񡢵绰������
	 * @param user ���޸ĵ��û�������Ϣ�����ٰ����û�id�����޸ĵ����ݰ������û������Ա�ͷ�񡢵绰������
	 * @return ��Ӱ�������
	 */
	Integer changeInfo(User user);
	
}








