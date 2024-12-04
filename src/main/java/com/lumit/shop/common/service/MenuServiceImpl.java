package com.lumit.shop.common.service;

import com.lumit.shop.common.model.TbMenu;
import com.lumit.shop.common.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;

    @Override
    public List<TbMenu> selectMenuList(String userId) {
        return menuRepository.selectMenuList(userId);
    }

    @Override
    public TbMenu selectMenuByMenuCd(String menuCd) {
        return menuRepository.selectMenuByMenuCd(menuCd);
    }

    @Override
    public List<TbMenu> selectMenuListByGroupCd(String groupCode) {
        return menuRepository.selectMenuListByGroupCd(groupCode);
    }
}
