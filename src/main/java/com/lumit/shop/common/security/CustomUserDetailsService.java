package com.lumit.shop.common.security;

import java.util.Iterator;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lumit.shop.common.model.TbMenu;
import com.lumit.shop.common.model.User;
import com.lumit.shop.common.repository.MenuRepository;
import com.lumit.shop.common.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    private final MenuRepository menuRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername :: " + username);
        // DB에서 User 객체 조회
        User user = userRepository.selectByUserName(username);

        if (user == null) {
            throw new UsernameNotFoundException("UsernameNotFoundException");
        }

        List<TbMenu> menuList = menuRepository.selectMenuList(username);

//        for (TbMenu menu : menuList) {
//            user.addAuthority(menu);
//        }


        for (Iterator<TbMenu> iterator = menuList.iterator(); iterator.hasNext(); ) {
            TbMenu menu = iterator.next();
            user.addAuthority(menu);
        }

        // 권한 정보 등록
//        List<GrantedAuthority> roles = new ArrayList<>();
//        roles.add(new SimpleGrantedAuthority(user.getRoleId()));
//
//        // AccountContext 생성자로 UserDetails 타입 생성
//        UserContext userContext = new UserContext(user,roles);

        return new PrincipalDetails(user);
    }
}
