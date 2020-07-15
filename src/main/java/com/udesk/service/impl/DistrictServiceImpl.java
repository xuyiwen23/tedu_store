package com.udesk.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udesk.entity.District;
import com.udesk.mapper.DistrictMapper;
import com.udesk.service.IDistrictService;



@Service("districtService")
public class DistrictServiceImpl implements IDistrictService {
	
	@Autowired
	private DistrictMapper districtMapper;

	public List<District> getList(String parent) {
		return districtMapper.getList(parent);
	}

	public District getInfo(String code) {
		return districtMapper.getInfo(code);
	}

}








