package com.lumit.shop.common.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lumit.shop.common.model.TbBoard;
import com.lumit.shop.common.model.TbFile;

@Mapper
@Repository
public interface FileRepository {
    public List<TbFile> selectFileList(TbFile files);

    public TbFile selectFile(TbFile files);
    
    public int insertFiles(TbFile files);
    
    public int deleteFiles(TbBoard board);
    
}

