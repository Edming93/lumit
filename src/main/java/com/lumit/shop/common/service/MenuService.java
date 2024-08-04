package com.lumit.shop.common.service;

import com.lumit.shop.common.model.TbMenu;

import java.util.List;

public interface MenuService {
    public List<TbMenu> getMenuList(String userId);
}
