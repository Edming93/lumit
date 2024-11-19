package com.lumit.shop.common.service;

import java.util.HashMap;

import com.lumit.shop.common.model.CommonSearch;

public interface CommonService {
	public HashMap<String,Object> selectCodeListByGrpCd(CommonSearch search);
	
}
