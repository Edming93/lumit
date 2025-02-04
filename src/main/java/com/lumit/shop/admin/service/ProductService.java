package com.lumit.shop.admin.service;

import java.util.HashMap;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.lumit.shop.common.model.CommonSearch;
import com.lumit.shop.common.model.TbProduct;

public interface ProductService {

    public HashMap<String, Object> selectProductList(CommonSearch search, Pageable pageable);
    public HashMap<String, Object> insertProduct(TbProduct product, MultipartFile[] files, MultipartFile[] filesRep);
    public HashMap<String, Object> detailProduct(TbProduct product);
    public HashMap<String, Object> updateProduct(TbProduct product);
    public HashMap<String, Object> deleteProduct(TbProduct product);
    
    public HashMap<String, Object> selectProductColorMappingList(TbProduct product);
    public HashMap<String, Object> selectProductTagMappingList(TbProduct product);
}
