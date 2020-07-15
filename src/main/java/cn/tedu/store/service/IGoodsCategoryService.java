package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.entity.GoodsCategory;

public interface IGoodsCategoryService {

	/**
	 * ���ݸ������࣬��ȡ�����б�
	 * @param parentId ���������id�������Ҫ��ȡһ�����࣬�򸸼�����Ϊ0
	 * @return �Ӽ������б�
	 */
	List<GoodsCategory> getListByParent(Long parentId);
	
}
