package cn.tedu.store.mapper;

import java.util.List;

import cn.tedu.store.entity.GoodsCategory;

public interface GoodsCategoryMapper {

	/**
	 * ���ݸ������࣬��ȡ�����б�
	 * @param parentId ���������id�������Ҫ��ȡһ�����࣬�򸸼�����Ϊ0
	 * @return �Ӽ������б�
	 */
	List<GoodsCategory> getListByParent(Long parentId);
	
}






