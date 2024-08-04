package com.lumit.shop.common.service;

import com.lumit.shop.common.model.TbMenu;
import com.lumit.shop.common.model.User;
import com.lumit.shop.common.repository.MenuRepository;
import com.lumit.shop.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;

    @Override
    public List<TbMenu> getMenuList(String userId) {
        return menuRepository.getMenuList(userId);
    }

}
