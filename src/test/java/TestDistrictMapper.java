import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.tedu.store.entity.District;
import cn.tedu.store.mapper.DistrictMapper;

public class TestDistrictMapper {

	private AbstractApplicationContext ac;
	private DistrictMapper districtMapper;
	
	@Test
	public void getList() {
		String parent = DistrictMapper.PROVINCE_PARENT;
		List<District> districts
			= districtMapper.getList(parent);
		for (District district : districts) {
			System.out.println(district);
		}
	}
	
	@Test
	public void getInfo() {
		District district
			= districtMapper.getInfo("130100");
		System.out.println(district);
	}
	
	@Before
	public void doBefore() {
		ac = new ClassPathXmlApplicationContext(
			"spring-dao.xml");
		districtMapper = ac.getBean(
			"districtMapper", DistrictMapper.class);
	}
	
	@After
	public void doAfter() {
		ac.close();
	}
}
