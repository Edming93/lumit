package com.lumit.shop.admin.service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lumit.shop.admin.repository.OptionRepository;
import com.lumit.shop.common.data.RequestList;
import com.lumit.shop.common.model.CommonSearch;
import com.lumit.shop.common.model.TbOption;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OptionServiceImpl implements OptionService {
    private final OptionRepository optionRepository;

    @Override
    public HashMap<String, Object> selectOptionList(CommonSearch search, Pageable pageable) {
    	HashMap<String,Object> retMap = new HashMap<String,Object>();
    	
    	retMap.put("list", this.selectPageableOptionList(search, pageable));
		retMap.put("total", optionRepository.selectCountOptionList(search));
    	
        return retMap;
    }
    
    public Page<Map<String, Object>> selectPageableOptionList(CommonSearch search, Pageable pageable) {
        RequestList<?> requestList = RequestList.builder().data(search).pageable(pageable).build();
        Field[] variables = requestList.getData().getClass().getDeclaredFields();

        System.out.println("search다요 :::");
        System.out.println(search);
        List<Map<String, Object>> content = optionRepository.selectPageableOptionList(requestList);
        int total = optionRepository.selectCountOptionList(search);
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public HashMap<String, Object> registOption(TbOption option) {
    	HashMap<String,Object> retMap = new HashMap<String,Object>();
    	
    	retMap.put("regist", optionRepository.insertOption(option));
    	
    	return retMap;
    }
    
    @Override
    public HashMap<String, Object> updateOption(TbOption option) {
    	HashMap<String,Object> retMap = new HashMap<String,Object>();
    	
    	retMap.put("update", optionRepository.updateOption(option));
    	
    	return retMap;
    }
    
    @Override
    public HashMap<String, Object> deleteOption(TbOption option) {
    	HashMap<String,Object> retMap = new HashMap<String,Object>();
    	
    	retMap.put("delete", optionRepository.deleteOption(option));
    	
    	return retMap;
    }
}
