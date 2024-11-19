package com.lumit.shop.common.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lumit.shop.common.model.CommonSearch;
import com.lumit.shop.common.repository.CodeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommonServiceImpl implements CommonService{
	
	@Autowired
	CodeRepository codeRepository;
	
	@Override
	public HashMap<String,Object> selectCodeListByGrpCd(CommonSearch search) {
		HashMap<String,Object> retMap = new HashMap<String,Object>();
		search.setUseYn("Y");
		
		retMap.put("list",codeRepository.selectCodeListByGrpCd(search));
	
		System.out.println(retMap);
		return retMap;
	}
}
