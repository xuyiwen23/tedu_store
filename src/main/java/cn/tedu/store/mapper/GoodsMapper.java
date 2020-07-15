package cn.tedu.store.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.Goods;

public interface GoodsMapper {

	/**
	 * ��ȡĳ�����µ���Ʒ�б�
	 * @param categoryId ��Ʒ�����id
	 * @param offset ƫ����������������������
	 * @param count ��Ҫ��ȡ�����ݵ�����
	 * @return ��Ʒ�б����û��ƥ������ݣ��򷵻���Ԫ�صļ���
	 */
	List<Goods> getListByCategory(
		    @Param("categoryId") Long categoryId, 
		    @Param("offset") Integer offset, 
		    @Param("count") Integer count);
	
	/**
	 * ������Ʒid��ȡ��Ʒ����
	 * @param id ��Ʒid
	 * @return ��Ʒ���ݣ����û��ƥ������ݣ��򷵻�null
	 */
	Goods getGoodsById(Long id);
	
}






