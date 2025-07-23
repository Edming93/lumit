package com.lumit.shop.common.service;

import com.lumit.shop.admin.dto.AdminDto;
import com.lumit.shop.common.constants.Role;
import com.lumit.shop.common.constants.ServiceCode;
import com.lumit.shop.common.data.Modal;
import com.lumit.shop.common.data.ModalInfo;
import com.lumit.shop.common.dto.SignUpDto;
import com.lumit.shop.common.dto.UserInfoDto;
import com.lumit.shop.common.model.TbAddress;
import com.lumit.shop.common.model.TbLogin;
import com.lumit.shop.common.model.User;
import com.lumit.shop.common.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.eclipse.angus.mail.imap.protocol.MODSEQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final HttpSession session;


    @Override
    public List<User> findRecentUsers() {
        return userRepository.findRecentUsers();
    }

    // TBLOGIN 관련
    @Override
    public List<User> selectUserList() {
        return userRepository.selectUserList();
    }

    @Override
    public List<User> selectAdminList() {
        return userRepository.selectAdminList();
    }

    @Override
    public List<User> sortWithCurrentUserFirst(List<User> users, String currentUserId) {
        return Stream.concat(
                users.stream().filter(u -> u.getUserId().equals(currentUserId)),
                users.stream().filter(u -> !u.getUserId().equals(currentUserId))
        ).collect(Collectors.toList());
    }

    @Override
    public TbLogin selectByUserId(String userId) {
        return userRepository.selectByUserId(userId);
    }

    @Override
    public TbLogin selectByEmail(String email) {
        return userRepository.selectByEmail(email);
    }

    @Override
    public int insertUser(TbLogin user) {
        return userRepository.insertUser(user);
    }

    public int insertAdmin(TbLogin user) {
        return userRepository.insertUser(user);
    }


    @Override
    @Transactional
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
        } catch (Exception e) {
            return ServiceCode.UNKNOWN;
        }
        return ServiceCode.SUCCESS;
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


    // TBADDRESS 관련
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

    @Override
    public ServiceCode updateAddress(TbAddress tbAddress) {
        int result = userRepository.updateAddress(tbAddress);
        if (result > 0) {
            return ServiceCode.UPDATED;
        }
        return ServiceCode.UNKNOWN;
    }

    @Override
    public ServiceCode deleteAddress(int id) {
        int result = userRepository.deleteAddress(id);
        if (result > 0) {
            return ServiceCode.DELETED;
        }
        return ServiceCode.UNKNOWN;
    }

    @Override
    public ServiceCode insertNewAddress(TbAddress tbAddress) {
        int result = userRepository.insertNewAddress(tbAddress);
        if (result > 0) {
            return ServiceCode.SUCCESS;
        }
        return ServiceCode.UNKNOWN;
    }

    @Override
    public List<TbAddress> selectAddressListByUserId(String userId) {
        return userRepository.selectAddressListByUserId(userId);
    }

    @Override
    public TbAddress selectAddressById(int id) {
        return userRepository.selectAddressById(id);
    }

    @Override
    public ServiceCode deleteAdmin(String id) {
        AdminDto adminDto = new AdminDto();
        User user = SecurityUtils.getPrincipal();
        if (!user.getRole().equals(Role.SUPER_ADMIN)) {
            return ServiceCode.UNAUTHORIZED;
        }
        adminDto.setModId(user.getUserId());
        adminDto.setUserId(id);
        int result = userRepository.deleteAdmin(adminDto);
        if (result <= 0) {
            return ServiceCode.UNKNOWN;
        }
        return ServiceCode.DELETED;
    }

    @Override
    public ServiceCode updateAdmin(String id, AdminDto adminDto) {
        User user = SecurityUtils.getPrincipal();
        if (!user.getRole().equals(Role.SUPER_ADMIN)) {
            return ServiceCode.FORBIDDEN;
        }
        adminDto.setModId(user.getUserId());
        adminDto.setUserId(id);
        int result = userRepository.updateAdmin(adminDto);
        if (result <= 0) {
            return ServiceCode.UNKNOWN;
        }
        return ServiceCode.UPDATED;
    }

    @Override
    public List<User> selectOldAdminList() {
        return userRepository.selectOldAdminList();
    }

    @Override
    public ServiceCode updateTempPwd(UserInfoDto userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        int result = userRepository.updatePwd(userInfo);
        if (result <= 0) {
            return ServiceCode.UNKNOWN;
        }
        return ServiceCode.UPDATED;
    }

    @Override
    public long countAllUsers() {
        return userRepository.countAllUsers();
    }

    @Override
    public long countUsersToday() {
        return userRepository.countUsersToday();
    }

    @Override
    public long countAdmins() {
        return userRepository.countAdmins();
    }

    @Override
    public ServiceCode updateUserInfo(UserInfoDto userInfo) {
        User user = userRepository.selectByUserId(userInfo.getUserId()).userMapping();
        String id = SecurityUtils.getPrincipal().getUserId();
        if (!id.equals(user.getUserId()) && !user.getRole().equals(Role.SUPER_ADMIN)) {
            return ServiceCode.UNAUTHORIZED;
        }
        if (userInfo.getName() != null) {
            if (user.getName().equals(userInfo.getName())) {
                return ServiceCode.NOT_MODIFIED;
            }
        }
        if (userInfo.getCurrent() != null) {
            if (!passwordEncoder.matches(userInfo.getCurrent(), user.getPassword())) {
                return ServiceCode.UNAUTHORIZED;
            }
            userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        }
        if (userInfo.getEmail() != null) {
            try {
                userRepository.removeAuthCode(userInfo.getUserId());
            } catch (Exception e) {
                return ServiceCode.UNKNOWN;
            }
        }
        userInfo.setUserId(id);
        userInfo.setModId(user.getUserId());
        int result = userRepository.updateUserInfo(userInfo);
        if (result < 1) {
            return ServiceCode.UNKNOWN;
        }
        SecurityUtils.refreshPrincipal();
        return ServiceCode.UPDATED;
    }

    // 유효성 검사 메소드
    @Override
    public boolean isIdDuplicated(String id) {
        TbLogin user = userRepository.selectByUserId(id);
        if (user != null) {
            return true;
        }
        return false;
    }
}
