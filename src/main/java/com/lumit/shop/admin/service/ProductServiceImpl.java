package com.lumit.shop.admin.service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lumit.shop.admin.repository.ProductRepository;
import com.lumit.shop.common.data.RequestList;
import com.lumit.shop.common.model.CommonSearch;
import com.lumit.shop.common.model.TbProduct;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    
    @Override
    public HashMap<String, Object> selectProductList(CommonSearch search, Pageable pageable) {
    	HashMap<String,Object> retMap = new HashMap<String,Object>();
    	
    	retMap.put("list", this.selectPageableProductList(search, pageable));
    	
        return retMap;
    }
    
    public Page<Map<String, Object>> selectPageableProductList(CommonSearch search, Pageable pageable) {
        RequestList<?> requestList = RequestList.builder().data(search).pageable(pageable).build();
        Field[] variables = requestList.getData().getClass().getDeclaredFields();

        List<Map<String, Object>> content = productRepository.selectPageableProductList(requestList);
        int total = productRepository.selectCountProductList(search);
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public HashMap<String, Object> registProduct(TbProduct product) {
    	HashMap<String,Object> retMap = new HashMap<String,Object>();
    	
    	retMap.put("regist", productRepository.insertProduct(product));
    	
    	return retMap;
    }
    
    @Override
    public HashMap<String, Object> updateProduct(TbProduct product) {
    	HashMap<String,Object> retMap = new HashMap<String,Object>();
    	
    	retMap.put("update", productRepository.updateProduct(product));
    	
    	return retMap;
    }
    
    @Override
    public HashMap<String, Object> deleteProduct(TbProduct product) {
    	HashMap<String,Object> retMap = new HashMap<String,Object>();
    	
    	retMap.put("delete", productRepository.deleteProduct(product));
    	
    	return retMap;
    }
    
}
