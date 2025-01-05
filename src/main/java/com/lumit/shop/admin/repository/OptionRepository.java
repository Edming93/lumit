package com.lumit.shop.admin.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lumit.shop.common.data.RequestList;
import com.lumit.shop.common.model.CommonSearch;
import com.lumit.shop.common.model.TbOption;

@Mapper
@Repository
public interface OptionRepository {
    public List<TbOption> selectOptionList(CommonSearch search);
    public List<Map<String, Object>> selectPageableOptionList(RequestList<?> requestList);
    public int selectCountOptionList(CommonSearch search);
    public int insertOption(TbOption option);
    public int updateOption(TbOption option);
    public int deleteOption(TbOption option);
}

