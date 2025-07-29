package com.lumit.shop.admin.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lumit.shop.common.data.RequestList;
import com.lumit.shop.common.model.CommonSearch;
import com.lumit.shop.common.model.TbMapping;
import com.lumit.shop.common.model.TbProduct;

@Mapper
@Repository
public interface ProductRepository {
    public List<Map<String, Object>> selectPageableProductList(RequestList<?> requestList);
    public int selectCountProductList(CommonSearch search);
    public int insertProduct(TbProduct product);
    public TbProduct selectProductDetail(TbProduct product);
    public int updateProduct(TbProduct product);
    public int deleteProduct(TbProduct product);
    
    public int deleteColorMap(TbProduct product);
    public int deleteTagMap(TbProduct product);
    
    public int insertColorMap(TbProduct product);
    public int insertTagMap(TbProduct product);
    public int insertProductCategoryMap(TbProduct product);
    
    public List<TbMapping> selectColorMapList(TbProduct product);
    public List<TbMapping> selectTagMapList(TbProduct product);
    public List<TbMapping> selectCateMapList(TbProduct product);
    

}


