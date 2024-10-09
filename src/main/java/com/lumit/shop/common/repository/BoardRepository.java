package com.lumit.shop.common.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.lumit.shop.common.dto.SearchDto;
import com.lumit.shop.common.model.TbBoard;

@Mapper
@Repository
public interface BoardRepository {
    public List<TbBoard> selectBoardList(SearchDto search);
	public int insertBoard(TbBoard board);
	public int updateBoard(TbBoard board);
	public int deleteBoard(@Param("menuCd")String menuCd, @Param("boardId") String boardId);
	public TbBoard selectBoardDetail(@Param("menuCd")String menuCd, @Param("boardId") String boardId);
	
}

