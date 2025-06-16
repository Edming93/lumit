package com.lumit.shop.common.service;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.lumit.shop.common.model.CommonSearch;
import com.lumit.shop.common.model.TbProduct;

public interface fileService {

    public HashMap<String, Object> insertImage(@RequestPart(value = "file" , required = false) MultipartFile file) throws IOException;

}
