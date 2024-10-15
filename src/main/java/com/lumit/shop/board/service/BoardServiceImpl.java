package com.lumit.shop.board.service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lumit.shop.common.data.RequestList;
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
    public TbBoard selectBoardDetail(String menuCd, String boardId) {
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
}
