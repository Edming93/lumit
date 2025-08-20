package com.lumit.shop.common.service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
import com.lumit.shop.admin.service.ProductService;
import com.lumit.shop.common.data.RequestList;
import com.lumit.shop.common.model.CommonSearch;
import com.lumit.shop.common.model.TbFile;
import com.lumit.shop.common.model.TbProduct;
import com.lumit.shop.common.repository.FileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class fileServiceImpl implements fileService {
    private final FileRepository fileRepository;
    
    @Value("${file.upload.path}")
    private String FILE_UPLOAD_PATH;

    @Override
    @Transactional
    public HashMap<String, Object> insertImage(MultipartFile file) throws IOException{
    	HashMap<String,Object> retMap = new HashMap<String,Object>();
    	
    	if (file == null || file.isEmpty()) throw new IOException("파일이 비어 있습니다");

    	try {
            String originalName = file.getOriginalFilename();
            String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1);
            List<String> allowedExt = List.of("jpg", "jpeg", "png", "gif", "webp", "jfif");

            if (!allowedExt.contains(ext.toLowerCase())) {
                throw new IOException("허용되지 않은 파일 형식입니다.");
            }

            String fileName = UUID.randomUUID() + "_" + originalName;
            Path path = Paths.get(FILE_UPLOAD_PATH +"/joditUpload/"+ fileName);
            Files.createDirectories(path.getParent());
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            // Jodit이 요구하는 JSON 구조에 맞춤
            // TODO ::  추후 서버로 동작할땐 경로 properties에서 가져다 쓰기
            retMap.put("images", List.of(fileName));          // 파일명 배열
            retMap.put("path", "/lumitFiles/joditUpload/");    // path
            retMap.put("baseurl", "/lumitFiles/joditUpload/");                // 실제 URL prefix
            retMap.put("error", false);                       // error: false
            retMap.put("msg", "업로드 성공");                 // 메시지

        } catch (Exception e) {
            retMap.put("error", true);
            retMap.put("msg", "업로드 실패: " + e.getMessage());
        }
    	
        // 웹에서 접근할 수 있는 URL 리턴
        return retMap;
    }
    
   
}
