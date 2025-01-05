package com.lumit.shop.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.lumit.shop.admin.repository.ProductRepository;
import com.lumit.shop.common.dto.SearchDto;
import com.lumit.shop.common.model.TbProduct;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    
    @Value("${file.upload.path}")
    private String FILE_UPLOAD_PATH;

    @Override
    public List<TbProduct> selectProductList(SearchDto search) {
        return productRepository.selectProductList(search);
    }

}
