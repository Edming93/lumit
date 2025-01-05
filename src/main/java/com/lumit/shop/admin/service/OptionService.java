package com.lumit.shop.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.lumit.shop.common.dto.SearchDto;
import com.lumit.shop.common.model.CommonSearch;
import com.lumit.shop.common.model.TbBoard;
import com.lumit.shop.common.model.TbFile;
import com.lumit.shop.common.model.TbOption;
import com.lumit.shop.common.model.TbProduct;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface OptionService {

    public HashMap<String, Object> selectOptionList(CommonSearch search, Pageable pageable);
    public Page<Map<String, Object>> selectPageableOptionList(CommonSearch search, Pageable pageable);
    public HashMap<String, Object> registOption(TbOption option);
    public HashMap<String, Object> updateOption(TbOption option);
    public HashMap<String, Object> deleteOption(TbOption option);
}
