package com.lumit.shop.common.service;

import com.lumit.shop.common.constants.ServiceCode;
import com.lumit.shop.common.dto.SignUpDto;
import com.lumit.shop.common.model.TbAddress;
import com.lumit.shop.common.model.TbLogin;
import com.lumit.shop.common.model.User;
import com.lumit.shop.common.repository.UserRepository;
import com.lumit.shop.common.security.social.CustomOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getUserList() {
        return userRepository.getUserList();
    }

    @Override
    public TbLogin selectByUserId(String userId) {
        return userRepository.selectByUserId(userId);
    }

    public User selectByUsername(String username) {
        return userRepository.selectByUserName(username);
    }

    @Override
    public int insertUser(TbLogin user) {
        return userRepository.insertUser(user);
    }

    public int insertAdmin(TbLogin user) {
        return userRepository.insertUser(user);
    }

    @Override
    public boolean isIdDuplicated(String id) {
        TbLogin user = userRepository.selectByUserId(id);
        if (user != null) {
            return true;
        }
        return false;
    }

    @Override
    public ServiceCode insertUserControl(SignUpDto user) {
        try {
            TbLogin tbLogin = selectByUserId(user.getUserId());
            if (tbLogin != null) {
                return ServiceCode.CONFLICT;
            }
            tbLogin = user.createTbLogin();
            tbLogin.setRegId(user.getUserId());
            tbLogin.setModId(user.getUserId());
            tbLogin.setPassword(passwordEncoder.encode(tbLogin.getPassword()));
            TbAddress address = TbAddress.createAddrByUser(tbLogin);
            insertUser(tbLogin);
            int addrId = insertAddress(address);
            if (user.getDefaultDeliveryAddr()) {
                tbLogin.setDefaultAddr(addrId);
                updateDefaultAddr(tbLogin);
            }
            System.out.println(user);
        } catch (Exception e) {
            return ServiceCode.UNKNOWN;
        }
        return ServiceCode.SUCCESS;
    }

    @Override
    public int insertAddress(TbAddress tbAddress) {
        try {
            int result = userRepository.insertAddress(tbAddress);
            if (result > 0) {
                List<TbAddress> tbAddressList = userRepository.selectAddressListByUserId(tbAddress.getUserId());
                return tbAddressList.get(0).getAddrId();
            }
        } catch (Exception e) {
            return 0;
        }
        return 0;
    }

    @Override
    public int updateDefaultAddr(TbLogin tbLogin) {
        return userRepository.updateDefaultAddr(tbLogin);
    }

    public ServiceCode updateSocialUser(SignUpDto signUpDto) {
        TbLogin user = userRepository.selectByUserId(signUpDto.getUserId());
        if (user != null) {
            return ServiceCode.CONFLICT;
        }
        user = signUpDto.createTbLogin();
        TbAddress address = TbAddress.createAddrByUser(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRegId(user.getUserId());
        user.setModId(user.getModId());
        int result = userRepository.updateSocialUser(user);
        if (signUpDto.getDefaultDeliveryAddr()) {
            int inserted = userRepository.insertAddress(address);
            if (inserted == 0) {
                return ServiceCode.UNKNOWN;
            }
            int addrId = userRepository.selectAddressListByUserId(signUpDto.getUserId()).get(0).getAddrId();
            user.setDefaultAddr(addrId);
            updateDefaultAddr(user);
        }
        if (result > 0) {
            return ServiceCode.UPDATED;
        }
        return ServiceCode.UNKNOWN;
    }

    @Override
    public List<TbAddress> selectAddressListByUserId(String userId) {
        return userRepository.selectAddressListByUserId(userId);
    }
}
