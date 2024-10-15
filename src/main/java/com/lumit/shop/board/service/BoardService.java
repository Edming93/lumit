package com.lumit.shop.board.service;

import java.util.List;
import java.util.Map;

import com.lumit.shop.common.dto.SearchDto;
import com.lumit.shop.common.model.TbBoard;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface BoardService {
	
	public List<TbBoard> selectBoardList(SearchDto search);
	public Map<String,Object> insertBoard(String menuCd, TbBoard board);
	public Map<String,Object> updateBoard(String menuCd, TbBoard board);
	public Map<String,Object> deleteBoard(TbBoard board);
	public TbBoard selectBoardDetail(String menuCd, String boardId, HttpServletRequest request, HttpServletResponse response);
	
}
