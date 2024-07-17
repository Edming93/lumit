package com.lumit.shop.common.security;

import com.lumit.shop.common.model.User;
import com.lumit.shop.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // DB에서 User 객체 조회
        User user = userRepository.selectByUserName(username);

        if(user == null) {
            throw new UsernameNotFoundException("UsernameNotFoundException");
        }

        // 권한 정보 등록
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(user.getRoleId()));

        // AccountContext 생성자로 UserDetails 타입 생성
        UserContext userContext = new UserContext(user,roles);

        return userContext;

    }
}
