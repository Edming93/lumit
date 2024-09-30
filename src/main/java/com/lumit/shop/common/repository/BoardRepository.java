package com.lumit.shop.common.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lumit.shop.common.model.TbBoard;

@Mapper
@Repository
public interface BoardRepository {

    public List<TbBoard> selectBoardList();
	public int insertBoard(TbBoard board);
}
