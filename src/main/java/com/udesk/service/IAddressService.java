package com.udesk.service;

import java.util.List;

import com.udesk.entity.Address;
import com.udesk.entity.User;
import com.udesk.service.ex.AddressNotFoundException;
import com.udesk.service.ex.ArgumentException;
import com.udesk.service.ex.InsertDataException;
import com.udesk.service.ex.PasswordNotMatchException;
import com.udesk.service.ex.UpdateDataException;
import com.udesk.service.ex.UserNotFoundException;
import com.udesk.service.ex.UsernameConflictException;

public interface IAddressService {

	/**
	 * �����µ��ջ���ַ��Ϣ
	 * @param currentUser ��ǰ�û����û���
	 * @param address �ջ���ַ��Ϣ
	 * @return �ɹ����ӵ��ջ���ַ��Ϣ������id
	 * @throws InsertDataException
	 */
	Address addnew(String currentUser, Address address) throws InsertDataException;

	/**
	 * ���ݵ�ǰ�û�id����ѯ���û����ջ���ַ������
	 * @param uid ��ǰ�û�id
	 * @return ���û����ջ���ַ������
	 */
	Integer getCountByUid(Integer uid);
	
	/**
	 * ��ȡĳ�û����ջ���ַ�б�
	 * @param uid �û���id
	 * @return �ջ���ַ�б�
	 */
	List<Address> getList(Integer uid);
	
	/**
	 * ����ĳ�û���ĳ���ջ���ַΪĬ���ջ���ַ
	 * @param uid �û�id
	 * @param id ������ΪĬ���ջ���ַ�����ݵ�id
	 */
	void setDefaultAddress(Integer uid, Integer id);
	
	/**
	 * ����id��ѯ�ջ���ַ����
	 * @param id ����id
	 * @return ƥ����ջ���ַ���ݣ����û��ƥ������ݣ��򷵻�null
	 */
	Address getAddressById(Integer id);
	
	/**
	 * ��ȡ������ӵ��ջ���ַ����
	 * @param uid �û�id
	 * @return �ջ���ַ���ݣ����û��ƥ������ݣ��򷵻�null
	 */
	Address getLatestAddress(Integer uid);
	
	/**
	 * ɾ���ջ���ַ����
	 * @param uid ��ǰ��¼���û�id
	 * @param id �ջ���ַ����id
	 * @throws AddressNotFoundException
	 * @throws ArgumentException
	 */
	void deleteById(Integer uid, Integer id) throws AddressNotFoundException, ArgumentException, UpdateDataException;
}