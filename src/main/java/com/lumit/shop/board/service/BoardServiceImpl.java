package com.lumit.shop.board.service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lumit.shop.common.data.RequestList;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lumit.shop.common.dto.SearchDto;
import com.lumit.shop.common.model.TbBoard;
import com.lumit.shop.common.repository.BoardRepository;
import com.lumit.shop.common.repository.MenuRepository;
import com.lumit.shop.common.service.SecurityUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final MenuRepository menuRepository;
    private final BoardRepository boardRepository;


    @Override
    public List<TbBoard> selectBoardList(SearchDto search) {
        return boardRepository.selectBoardList(search);
    }

    @Override
    public Page<Map<String, Object>> selectPageableBoardList(TbBoard tbBoard, Pageable pageable) {
        RequestList<?> requestList = RequestList.builder().data(tbBoard).pageable(pageable).build();
        Field[] variables = requestList.getData().getClass().getDeclaredFields();
        for (Field field : variables) {
            System.out.println(field.getName());
        }

        List<Map<String, Object>> content = boardRepository.selectPageableBoardList(requestList);
        int total = boardRepository.selectListBoardCount(tbBoard);

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Map<String, Object> insertBoard(String menuCd, TbBoard board) {
        Map<String, Object> result = new HashMap<String, Object>();

        board.setMenuCd(menuCd);
        board.setMenuDvCd(menuRepository.selectMenuByMenuCd(menuCd).getTmplCd());
        board.setUseYn("Y");
        board.setDelYn("N");
        board.setRplyYn("N");
        board.setFileYn("N");
        board.setViewCount("0");
        board.setRegId(SecurityUtils.getPrincipal().getUserId());
        board.setModId(SecurityUtils.getPrincipal().getUserId());

        boardRepository.insertBoard(board);

        result.put("result", "success");

        return result;
    }

    @Override
    public TbBoard selectBoardDetail(String menuCd, String boardId, HttpServletRequest request, HttpServletResponse response) {
        TbBoard board = new TbBoard();
        board.setBoardId(boardId);
        board.setMenuCd(menuCd);
        board.setModId(SecurityUtils.getPrincipal().getUserId());
        board.setViewCount("set");
        // 조회수 카운트
        this.viewCount(board, request, response);

        return boardRepository.selectBoardDetail(menuCd, boardId);
    }

    @Override
    public Map<String, Object> updateBoard(String menuCd, TbBoard board) {
        Map<String, Object> result = new HashMap<String, Object>();

        board.setModId(SecurityUtils.getPrincipal().getUserId());
        boardRepository.updateBoard(board);

        result.put("result", "success");

        return result;
    }

    @Override
    public Map<String, Object> deleteBoard(TbBoard board) {
        Map<String, Object> result = new HashMap<String, Object>();

        board.setModId(SecurityUtils.getPrincipal().getUserId());
        board.setDelYn("Y");
        boardRepository.updateBoard(board);

        result.put("result", "success");

        return result;
    }

    private void viewCount(TbBoard board, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("왜안타");
        Cookie oldCookie = null;

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("postView")) {
                    oldCookie = cookie;
                }
            }
        }

        // oldCookie에 값이 있다면, 즉 postView가 존재한다면
        // 해당 쿠키의 value가 현재 접근한 게시글의 번호(bbsNum)를 포함하고 있는지 검사한다.
        if (oldCookie != null) {
            // 단순히 숫자만 사용하면 문제가 생길 수 있어서 괄호로 감싸 숫자를 온전히 검사하고자 함
            if (!oldCookie.getValue().contains("[" + board.getBoardId() + "]")) {
                boardRepository.updateBoard(board);
                oldCookie.setValue(oldCookie.getValue() + "_[" + board.getBoardId() + "]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(1800);
                response.addCookie(oldCookie);
            }
            // null이면 DB조회수 올리고
            // 해당 게시글 id를 괄호로 감싼 새로운 쿠키 postView를 생성하여 HttpServletResponse에게 전달한다.
        } else {
            boardRepository.updateBoard(board);

            Cookie newCookie = new Cookie("postView", "[" + board.getBoardId() + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(1800);
            response.addCookie(newCookie);
        }

    }
}
