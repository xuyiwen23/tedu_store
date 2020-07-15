package cn.tedu.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.store.entity.District;
import cn.tedu.store.entity.ResponseResult;
import cn.tedu.store.service.IDistrictService;

@Controller
@RequestMapping("/district")
public class DistrictController extends BaseController {
	
	@Autowired
	private IDistrictService districtService;

	@RequestMapping("/list.do")
	@ResponseBody
	public ResponseResult<List<District>> getList(
		@RequestParam("parent") String parent) {
		// 声明返回值对象
		ResponseResult<List<District>> rr
			= new ResponseResult<List<District>>();
		// 调用业务层对象获取数据
		List<District> districts
			= districtService.getList(parent);
		// 封装需要响应的数据
		rr.setData(districts);
		// 执行返回
		return rr;
	}
	
	@RequestMapping("/info.do")
	@ResponseBody
	public ResponseResult<District> getInfo(
		@RequestParam("code") String code) {
		// 声明返回值对象
		ResponseResult<District> rr
			= new ResponseResult<District>();
		// 调用业务层对象获取数据
		District district
			= districtService.getInfo(code);
		// 封装需要响应的数据
		rr.setData(district);
		// 执行返回
		return rr;
	}
	
}







