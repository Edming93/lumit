package com.lumit.shop.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.lumit.shop.common.dto.SearchDto;
import com.lumit.shop.common.model.TbBoard;
import com.lumit.shop.common.model.TbFile;
import com.lumit.shop.common.model.TbProduct;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface ProductService {

    public List<TbProduct> selectProductList(SearchDto search);
}
