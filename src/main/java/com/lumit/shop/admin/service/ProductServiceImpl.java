package com.lumit.shop.admin.service;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lumit.shop.admin.repository.ProductRepository;
import com.lumit.shop.common.data.RequestList;
import com.lumit.shop.common.model.CommonSearch;
import com.lumit.shop.common.model.TbFile;
import com.lumit.shop.common.model.TbProduct;
import com.lumit.shop.common.repository.FileRepository;
import com.lumit.shop.common.service.SecurityUtils;
import com.lumit.shop.common.service.StringUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final FileRepository fileRepository;
    
    @Value("${file.upload.path}")
    private String FILE_UPLOAD_PATH;
    
    @Override
    public HashMap<String, Object> selectProductList(CommonSearch search, Pageable pageable) {
    	HashMap<String,Object> retMap = new HashMap<String,Object>();
    	
    	retMap.put("list", this.selectPageableProductList(search, pageable));
    	
        return retMap;
    }
    
    public Page<Map<String, Object>> selectPageableProductList(CommonSearch search, Pageable pageable) {
    	search.setMenuCd("M201");
    	search.setFileDvCd("1001");
    	
        RequestList<?> requestList = RequestList.builder().data(search).pageable(pageable).build();
        Field[] variables = requestList.getData().getClass().getDeclaredFields();

        List<Map<String, Object>> content = productRepository.selectPageableProductList(requestList);
        System.out.println("----------");
        System.out.println(productRepository.selectPageableProductList(requestList));
        int total = productRepository.selectCountProductList(search);
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    @Transactional
    public HashMap<String, Object> insertProduct(TbProduct product, MultipartFile[] files, MultipartFile[] filesRep) {
    	HashMap<String,Object> retMap = new HashMap<String,Object>();
    	
    	product.setSales("0");
    	product.setDelYn("N");
    	product.setRegId(SecurityUtils.getPrincipal().getUserId());
    	product.setModId(SecurityUtils.getPrincipal().getUserId());
    	
    	retMap.put("insert", productRepository.insertProduct(product));
    	System.out.println("product ::: ");
    	System.out.println(product.getColorIdList());
    	System.out.println(product.getTagIdList());
    	
    	// 옵션 테이블에 데이터 추가
    	if(product.getColorIdList().size() != 0) productRepository.insertColorMap(product);
    	if(product.getTagIdList().size() != 0) productRepository.insertTagMap(product);
    	
    	if(filesRep != null || files != null) {
    		uploadFiles(product,files,filesRep);
    		
    		retMap.put("fileInsert","success");
    	}
    	
    	return retMap;
    }
    
    @Transactional
    public void uploadFiles(TbProduct product, MultipartFile[] files, MultipartFile[] filesRep) {
    	TbFile inputFile = new TbFile();
    	inputFile.setPkId(product.getProductId());

    	// boardId 해당 게시물의 파일을 모두 삭제하고 다시 추가
		fileRepository.deleteFiles(inputFile);
	
    	File uploadPath = new File(FILE_UPLOAD_PATH, StringUtils.getData());
    	
    	System.out.println("upload path: "+ uploadPath);
    	
    	if(uploadPath.exists() == false) {
    		uploadPath.mkdirs();
    	}
    	
    	if(product.getJsonFileList() != null) {
    		System.out.println("file ::: 기존파일추가  --------------------------");
    		
    		// List<String>의 형태를 List<TbFile>로 변환
    		ObjectMapper mapper = new ObjectMapper();
    		List<TbFile> fileList = new ArrayList<>();
    		
    		for (String jsonFile : product.getJsonFileList()) {
    			
				TbFile file;
				try {
					file = mapper.readValue(jsonFile, TbFile.class);
					
					fileList.add(file);
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
				
			}
    		
	    	// 기존 파일 DB추가
	    	for (TbFile file : fileList) {
				fileRepository.insertFiles(file);
			}
    	}
    	
    	if(filesRep != null) {
    		System.out.println("file ::: 대표 이미지 새 파일 추가 --------------------------");
	    	// 새로운 파일 DB추가
	    	for(MultipartFile file : filesRep) {
	    		String oriFileName =  file.getOriginalFilename();
	    		
	    		UUID uuid = UUID.randomUUID(); // 랜덤 이름 생성
	    		
	    		String uploadFileName = uuid.toString() + "_" + oriFileName; //UUID(랜덤문자라생각하면편함) + 원본파일명
	    		
	    		File saveFile = new File(uploadPath, uploadFileName);
	    		
	    		TbFile tbFile = new TbFile();
	    		tbFile.setPkId(product.getProductId());
	    		tbFile.setFileDvCd("1001"); // 대표 이미지  
	    		tbFile.setMenuCd("M201");
	    		tbFile.setFileName(oriFileName);
	    		tbFile.setFileNewName(uploadFileName);
	    		tbFile.setFileSize(file.getSize()+"");
	    		tbFile.setFilePath(uploadPath+"");
	    		// 01 : 서버
	    		tbFile.setFileType("01");
	    		tbFile.setFileExtension(StringUtils.getFileExtension(oriFileName));
	    		tbFile.setRegId(SecurityUtils.getPrincipal().getRegId());
	    		
	    		try {
	    			file.transferTo(saveFile); //물리적인 파일을 해당경로에 저장한다.
	
	        		fileRepository.insertFiles(tbFile);
				}catch(Exception e) {
					// log.error(e.getMessage());
					// log.error("error : ",e);
				}
	    	}
    	}
    	
    	if(files != null) {
    		System.out.println("file ::: 상세 이미지 새 파일 추가 --------------------------");
	    	// 새로운 파일 DB추가
	    	for(MultipartFile file : files) {
	    		String oriFileName =  file.getOriginalFilename();
	    		
	    		UUID uuid = UUID.randomUUID(); // 랜덤 이름 생성
	    		
	    		String uploadFileName = uuid.toString() + "_" + oriFileName; //UUID(랜덤문자라생각하면편함) + 원본파일명
	    		
	    		File saveFile = new File(uploadPath, uploadFileName);
	    		
	    		TbFile tbFile = new TbFile();
	    		tbFile.setPkId(product.getProductId());
	    		tbFile.setFileDvCd("1002"); // 대표 이미지  
	    		tbFile.setMenuCd("M201");
	    		tbFile.setFileName(oriFileName);
	    		tbFile.setFileNewName(uploadFileName);
	    		tbFile.setFileSize(file.getSize()+"");
	    		tbFile.setFilePath(uploadPath+"");
	    		// 01 : 서버
	    		tbFile.setFileType("01");
	    		tbFile.setFileExtension(StringUtils.getFileExtension(oriFileName));
	    		tbFile.setRegId(SecurityUtils.getPrincipal().getRegId());
	    		
	    		try {
	    			file.transferTo(saveFile); //물리적인 파일을 해당경로에 저장한다.
	
	        		fileRepository.insertFiles(tbFile);
				}catch(Exception e) {
					// log.error(e.getMessage());
					// log.error("error : ",e);
				}
	    	}
    	}
    }
    
    @Override
    public HashMap<String, Object> detailProduct(TbProduct product) {
    	HashMap<String,Object> retMap = new HashMap<String,Object>();
    	System.out.println("detail :::");
    	System.out.println(productRepository.selectProductDetail(product));
    	retMap.put("detail", productRepository.selectProductDetail(product));
    	TbFile files = new TbFile();
    	files.setMenuCd("M201");
    	files.setPkId(product.getProductId());
    	retMap.put("files", fileRepository.selectFileList(files));
    	
    	return retMap;
    }
    
    @Override
    public HashMap<String, Object> updateProduct(TbProduct product) {
    	HashMap<String,Object> retMap = new HashMap<String,Object>();
    	
    	retMap.put("update", productRepository.updateProduct(product));
    	
    	return retMap;
    }
    
    @Override
    public HashMap<String, Object> deleteProduct(TbProduct product) {
    	HashMap<String,Object> retMap = new HashMap<String,Object>();
    	
    	retMap.put("delete", productRepository.deleteProduct(product));
    	
    	return retMap;
    }
    
    @Override
    public HashMap<String, Object> selectProductColorMappingList(TbProduct product) {
    	HashMap<String,Object> retMap = new HashMap<String,Object>();
    	retMap.put("colorList", productRepository.selectColorMapList(product));
    	
    	return retMap;
    }
    
    @Override
    public HashMap<String, Object> selectProductTagMappingList(TbProduct product) {
    	HashMap<String,Object> retMap = new HashMap<String,Object>();
    	retMap.put("tagList", productRepository.selectTagMapList(product));
    	
    	return retMap;
    }
}
