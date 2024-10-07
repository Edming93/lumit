package com.lumit.shop.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public Map<String,Object> insertBoard(String menuCd, TbBoard board) {
		Map<String, Object> result = new HashMap<String,Object>();
		
		board.setMenuCd(menuCd);
		board.setMenuDvCd(menuRepository.selectMenuByMenuCd(menuCd).getTmplCd());
		board.setUseYn("Y");
		board.setDelYn("N");
		board.setRplyYn("Y");
		board.setFileYn("N");
		board.setViewCount("0");
		board.setRegId(SecurityUtils.getPrincipal().getUserId());
		board.setModId(SecurityUtils.getPrincipal().getUserId());
		
		boardRepository.insertBoard(board);
		
		result.put("result", "success");
		
		return result;
	}

	@Override
	public TbBoard selectBoardDetail(String boardId) {
		return boardRepository.selectBoardDetail(boardId);
	}
}
