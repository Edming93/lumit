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


public class APIController {


}
