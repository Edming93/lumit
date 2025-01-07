package com.lumit.shop.common.controller;

import com.google.gson.JsonObject;
import com.lumit.shop.admin.dto.AdminDto;
import com.lumit.shop.board.service.BoardService;
import com.lumit.shop.common.constants.Role;
import com.lumit.shop.common.constants.ServiceCode;
import com.lumit.shop.common.data.Modal;
import com.lumit.shop.common.data.ModalInfo;
import com.lumit.shop.common.dto.ResponseDto;
import com.lumit.shop.common.dto.SearchDto;
import com.lumit.shop.common.dto.UserInfoDto;
import com.lumit.shop.common.model.*;
import com.lumit.shop.common.security.PrincipalDetails;
import com.lumit.shop.common.service.CommonService;
import com.lumit.shop.common.service.MenuService;
import com.lumit.shop.common.service.SecurityUtils;
import com.lumit.shop.common.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class APIController {

    private final UserService userService;
    private final BoardService boardService;
    private final MenuService menuService;
    private final PasswordEncoder passwordEncoder;
    private final HttpSession session;

    // 멤버 - 회원가입 - 중복체크api
    @GetMapping(value = "/user/idCheck")
    public @ResponseBody ResponseDto<?> idDuplicateCheck(@RequestParam(value = "id") String id) {
        if (id == null || id.isEmpty()) {
            return new ResponseDto<>("아이디를 입력해주세요", null);
        }
        if (userService.isIdDuplicated(id)) {
            return new ResponseDto<>("동일한 아이디가 존재합니다.", false);
        } else {
            return new ResponseDto<>("사용가능한 아이디입니다.", true);
        }
    }

    /**
     * 개방된 api 메소드 매개 변수에 userId를 넣는 것이 보안의 문제가 있다고 판단되어
     * authentication의 정보를 얻어와서 반환하는 것으로 변경하였습니다.
     *
     * @return
     */
    @GetMapping(value = "/user/address")
    public @ResponseBody ResponseDto<?> selectAddressList() throws IOException {
        User user = SecurityUtils.getPrincipal();
        if (user == null) {
            return null;
        }
        List<TbAddress> addressList = userService.selectAddressListByUserId(user.getUserId());
        return new ResponseDto<>("", addressList);
    }

    @PostMapping(value = "/user/address")
    public @ResponseBody ResponseEntity insertNewAddress(@RequestBody TbAddress data) {
        User user = SecurityUtils.getPrincipal();
        data.setUserId(user.getUserId());
        data.setPhoneNumber(data.getPhoneNumber().replace("-", ""));
        ServiceCode sc = userService.insertNewAddress(data);
        if (!sc.equals(ServiceCode.SUCCESS)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/user/address/{id}")
    public @ResponseBody ResponseEntity updateAddrById(@PathVariable("id") int id, @RequestBody TbAddress data) {
        User user = SecurityUtils.getPrincipal();
        data.setPhoneNumber(data.getPhoneNumber().replace("-", ""));
        ServiceCode sc = userService.updateAddress(data);
        if (!sc.equals(ServiceCode.UPDATED)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(value = "/user/address/{id}")
    public @ResponseBody ResponseEntity deleteAddrById(@PathVariable("id") int id) {
        User user = SecurityUtils.getPrincipal();
        TbAddress tbAddress = userService.selectAddressById(id);
        if (!tbAddress.getUserId().equals(user.getUserId())) {
            return ResponseEntity.status(401).build();
        }
        ServiceCode sc = userService.deleteAddress(id);
        if (!sc.equals(ServiceCode.DELETED)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }


    @GetMapping(value = "/user/address/default/{id}")
    public @ResponseBody ResponseEntity updateDefaultAddr(@PathVariable("id") int id) throws IOException {
        User user = SecurityUtils.getPrincipal();
        if (user == null) {
            return null;
        }
        UserInfoDto infoDto = UserInfoDto.builder().userId(user.getUserId()).defaultAddr(id).build();
        ServiceCode sc = userService.updateUserInfo(infoDto);
        List<TbAddress> addressList = userService.selectAddressListByUserId(user.getUserId());
        if (!sc.equals(ServiceCode.UPDATED)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(addressList);
    }

    @PatchMapping(value = "/user/password")
    public @ResponseBody ResponseEntity<?> changePwd(@RequestBody UserInfoDto userInfo) {
        User user = SecurityUtils.getPrincipal();
        userInfo.setUserId(user.getUserId());
        if (!passwordEncoder.matches(userInfo.getCurrent(), user.getPassword())) {
            return ResponseEntity.badRequest().build();
        }
        ServiceCode sc = userService.updateTempPwd(userInfo);
        if (!sc.equals(ServiceCode.UPDATED)) {
            return ResponseEntity.status(500).build();
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/user/info/{id}")
    public @ResponseBody ResponseEntity<?> updateInfo(@PathVariable("id") String id, @RequestBody UserInfoDto userInfo) {
        userInfo.setUserId(id);
        ServiceCode sc = userService.updateUserInfo(userInfo);
        setModalSession(userInfo, sc);
        return ResponseEntity.status(200).build();
    }

    @GetMapping(value = "/boards")
    public @ResponseBody ResponseEntity<?> boardList(String menuCd, SearchDto search, TbBoard board, @PageableDefault(size = 10) Pageable pageable) throws IOException {
        System.out.println(menuCd);
        System.out.println("-------------");
        board.setMenuCd(menuCd);
        if (search.getTitle() != null) {
            board.setTitle(search.getTitle());
        }

        if (search.getCategories() != null) {
            board.setCategories(search.getCategories());
        }
        return ResponseEntity.ok(boardService.selectPageableBoardList(board, pageable));
    }

    @DeleteMapping(value = "/admin/info/{id}")
    public @ResponseBody ResponseEntity<?> deleteRole(@PathVariable("id") String id) {
        ServiceCode result = userService.deleteAdmin(id);
        if (!result.equals(ServiceCode.DELETED)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/admin/info/{id}")
    public @ResponseBody ResponseEntity<?> updateAdmin(@PathVariable("id") String id, @RequestBody AdminDto adminDto) {
        ServiceCode result = userService.updateAdmin(id, adminDto);
        if (!result.equals(ServiceCode.UPDATED)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/common/code/{groupCode}")
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

    private void setModalSession(UserInfoDto userInfoDto, ServiceCode sc) {
        String title = "회원 정보 변경";
        String content = "정보를 수정하지 못하였습니다.<br>잠시 후 다시 시도해 주세요.";
        if (userInfoDto.getName() != null) {
            title = "닉네임 변경";
            if (sc.equals(ServiceCode.NOT_MODIFIED)) {
                content = "변경할 정보가 없습니다.";
            } else {
                content = "닉네임이 변경되었습니다.";
            }
        } else if (userInfoDto.getPassword() != null) {
            title = "비밀번호 변경";
            if (sc.equals(ServiceCode.UNAUTHORIZED)) {
                content = "비밀번호가 틀렸습니다.<br>확인 후 다시 시도해주세요.";
            } else if (sc.equals(ServiceCode.UPDATED)) {
                content = "비밀번호가 수정되었습니다.";
            }
        }
        Modal modal = Modal.builder().title(title).content(content).build();
        session.setAttribute("modal", modal);
    }
}
