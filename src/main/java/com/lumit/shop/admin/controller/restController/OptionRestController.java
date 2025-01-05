package com.lumit.shop.admin.controller.restController;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lumit.shop.admin.service.OptionService;
import com.lumit.shop.common.config.ResponseBuilder;
import com.lumit.shop.common.model.CommonSearch;
import com.lumit.shop.common.model.TbOption;
import com.lumit.shop.common.repository.MenuRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/rest/{siteId}/option")
public class OptionRestController {
    private final OptionService optionService;
    
    @Autowired
    MenuRepository menuRepository;

    @ResponseBody
    @GetMapping("/list")
    public ResponseEntity<?> selectOptionList(CommonSearch search,@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(optionService.selectPageableOptionList(search,pageable));
    }
    
    @ResponseBody
    @PostMapping("/regist")
    public ResponseEntity<Map<String,Object>> registOption(@RequestBody TbOption option) {
        return ResponseBuilder.build(optionService.registOption(option),HttpStatus.OK);
    }
    
    @ResponseBody
    @PostMapping("/update")
    public ResponseEntity<Map<String,Object>> updateOption(@RequestBody TbOption option) {
        return ResponseBuilder.build(optionService.updateOption(option),HttpStatus.OK);
    }
    
    @ResponseBody
    @PostMapping("/delete")
    public ResponseEntity<Map<String,Object>> deleteOption(@RequestBody TbOption option) {
        return ResponseBuilder.build(optionService.deleteOption(option),HttpStatus.OK);
    }

} 
