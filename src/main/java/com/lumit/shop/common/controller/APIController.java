package com.lumit.shop.common.controller;

import com.lumit.shop.admin.dto.AdminDto;
import com.lumit.shop.board.service.BoardService;
import com.lumit.shop.common.constants.Role;
import com.lumit.shop.common.constants.ServiceCode;
import com.lumit.shop.common.dto.ResponseDto;
import com.lumit.shop.common.dto.SearchDto;
import com.lumit.shop.common.dto.UserInfoDto;
import com.lumit.shop.common.model.CommonSearch;
import com.lumit.shop.common.model.TbAddress;
import com.lumit.shop.common.model.TbBoard;
import com.lumit.shop.common.model.User;
import com.lumit.shop.common.security.PrincipalDetails;
import com.lumit.shop.common.service.CommonService;
import com.lumit.shop.common.service.SecurityUtils;
import com.lumit.shop.common.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class APIController {

    private final UserService userService;
    private final BoardService boardService;
    private final CommonService commonService;
    private final PasswordEncoder passwordEncoder;

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
    @GetMapping(value = "/user/addresses")
    public @ResponseBody ResponseDto<?> selectAddressList() throws IOException {
        User user = SecurityUtils.getPrincipal();
        if (user == null) {
            return null;
        }
        List<TbAddress> addressList = userService.selectAddressListByUserId(user.getUserId());
        return new ResponseDto<>("", addressList);
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
        ServiceCode sc = userService.updateUserInfo(id, userInfo);
        if (sc.equals(ServiceCode.CONFLICT)) {
            return ResponseEntity.status(409).build();
        }
        if (!sc.equals(ServiceCode.UPDATED)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/boards")
    public @ResponseBody ResponseEntity<?> boardList(String menuCd, SearchDto search, TbBoard board, @PageableDefault(size = 10) Pageable pageable) throws IOException {
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
        Map<String, Object> result = commonService.selectCodeListByGrpCd(commonSearch);
        if (result == null || result.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(result);
    }
}
