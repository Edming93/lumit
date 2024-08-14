package com.lumit.shop.common.repository;

import com.lumit.shop.common.model.KakaoUser;
import com.lumit.shop.common.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserRepository {

    List<User> getUserList();

    User selectByUserName(String username);

    // 관리자에서만 사용하는건 관리자에 repository 따로 파주세용~~ (model은 같은 값을 사용하는거면 common User 하나로 통일해도 될것 같아요~)
    // common은 공통으로 사용할 수 있는 것만 작성
    // 이름은 앞에 regist/update/select/delete 맞춰주는걸로 하면 어떨까요~~
    int addNewUser(com.lumit.shop.admin.model.User user);

    com.lumit.shop.admin.model.User findUserById(String userId);

    User selectUserByKakaoId(String id);
    int insertUser(com.lumit.shop.admin.model.User user);
}
