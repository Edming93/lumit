package com.lumit.shop.common.controller.rest;

import com.lumit.shop.common.model.CommonSearch;
import com.lumit.shop.common.model.TbMenu;
import com.lumit.shop.common.service.MenuService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/common")
public class CommonRestController {
    private final MenuService menuService;

    @GetMapping(value = "/code/{groupCode}")
    public @ResponseBody ResponseEntity<?> getCode(@PathVariable("groupCode") String groupCode) {
        CommonSearch commonSearch = new CommonSearch();
        commonSearch.setGrpCd(groupCode);
        commonSearch.setUseYn("Y");
        List<TbMenu> result = menuService.selectMenuListByGroupCd(groupCode);
        if (result == null || result.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(result);
    }
}
