package com.lumit.shop.board.service;

import java.util.List;
import java.util.Map;

import com.lumit.shop.common.model.TbBoard;

public interface BoardService {
	
	public List<TbBoard> selectBoardList();
	public Map<String,Object> insertBoard(String menuCd, TbBoard board);
}
