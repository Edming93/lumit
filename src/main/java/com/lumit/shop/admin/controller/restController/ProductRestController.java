package com.lumit.shop.admin.controller.restController;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.lumit.shop.admin.service.ProductService;
import com.lumit.shop.common.config.ResponseBuilder;
import com.lumit.shop.common.model.CommonSearch;
import com.lumit.shop.common.model.TbProduct;
import com.lumit.shop.common.repository.MenuRepository;
import com.lumit.shop.common.service.fileService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/rest/{siteId}/product")
public class ProductRestController {
    private final ProductService productService;
    private final fileService fileService;
    
    @Autowired
    MenuRepository menuRepository;

    @GetMapping("/list")
    public ResponseEntity<Map<String,Object>> selectProductList(CommonSearch search, @PageableDefault(size = 10) Pageable pageable) {
        return ResponseBuilder.build(productService.selectProductList(search, pageable),HttpStatus.OK);
    }
    
    @ResponseBody
    @PostMapping("/regist")
    public ResponseEntity<Map<String,Object>> registProduct(@ModelAttribute TbProduct product, 
															@RequestPart(value = "files", required = false) MultipartFile[] files
															, @RequestPart(value = "filesRep", required = false) MultipartFile[] filesRep) {
        return ResponseBuilder.build(productService.insertProduct(product,files,filesRep),HttpStatus.OK);
    }
    
    @ResponseBody
    @PostMapping("/upload-image")
    public ResponseEntity<Map<String,Object>> registProductUploadImage(@RequestPart(value = "file" , required = false) MultipartFile file) throws IOException {
        return ResponseBuilder.build(fileService.insertImage(file),HttpStatus.OK);
    }
    
    @ResponseBody
    @PostMapping("/detail")
    public ResponseEntity<Map<String,Object>> selectProductDetail(@RequestBody TbProduct product) {
        return ResponseBuilder.build(productService.detailProduct(product),HttpStatus.OK);
    }
    
    @ResponseBody
    @PostMapping("/update")
    public ResponseEntity<Map<String,Object>> updateProduct(@RequestBody TbProduct product) {
        return ResponseBuilder.build(productService.updateProduct(product),HttpStatus.OK);
    }
    
    @ResponseBody
    @PostMapping("/delete")
    public ResponseEntity<Map<String,Object>> deleteProduct(@RequestBody TbProduct product) {
        return ResponseBuilder.build(productService.deleteProduct(product),HttpStatus.OK);
    }
    
    @ResponseBody
    @PostMapping("/color-list")
    public ResponseEntity<Map<String,Object>> selectProductColorList(@RequestBody TbProduct product) {
        return ResponseBuilder.build(productService.selectProductColorMappingList(product),HttpStatus.OK);
    }
    
    @ResponseBody
    @PostMapping("/tag-list")
    public ResponseEntity<Map<String,Object>> selectProductTagList(@RequestBody TbProduct product) {
        return ResponseBuilder.build(productService.selectProductTagMappingList(product),HttpStatus.OK);
    }

} 
