package com.udesk.mapper;

import java.util.List;

import com.udesk.entity.District;

public interface DistrictMapper {
	/**
	 * ��ȡʡ/��/�����б�
	 * @param parent �����Ĵ��ţ������ȡ�е��б�ʱ��Ӧ��ʹ���������ʡ�Ĵ��ţ������Ҫ��ȡʡ�б�Ӧ��ʹ��{@link #PROVINCE_PARENT}��Ϊ����
	 * @return ʡ/��/�����б�
	 * @see #PROVINCE_PARENT
	 */
	List<District> getList(String parent);

	/**
	 * ��ȡĳ��ʡ/��/������ϸ��Ϣ
	 * @param code ʡ/��/���Ĵ���
	 * @return ʡ/��/������ϸ��Ϣ�����û��ƥ������ݣ��򷵻�null
	 */
	District getInfo(String code);
	
}









