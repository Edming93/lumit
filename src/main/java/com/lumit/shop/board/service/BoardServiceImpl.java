package com.lumit.shop.board.service;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lumit.shop.common.data.RequestList;
import com.lumit.shop.common.dto.SearchDto;
import com.lumit.shop.common.model.TbBoard;
import com.lumit.shop.common.model.TbFile;
import com.lumit.shop.common.repository.BoardRepository;
import com.lumit.shop.common.repository.FileRepository;
import com.lumit.shop.common.repository.MenuRepository;
import com.lumit.shop.common.service.SecurityUtils;
import com.lumit.shop.common.service.StringUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final MenuRepository menuRepository;
    private final BoardRepository boardRepository;
    private final FileRepository fileRepository;

    @Value("${file.upload.path}")
    private String FILE_UPLOAD_PATH;

    @Override
    public List<TbBoard> selectBoardList(SearchDto search) {
        return boardRepository.selectBoardList(search);
    }

    @Override
    public Page<Map<String, Object>> selectPageableBoardList(TbBoard tbBoard, Pageable pageable) {
        RequestList<?> requestList = RequestList.builder().data(tbBoard).pageable(pageable).build();
        Field[] variables = requestList.getData().getClass().getDeclaredFields();
//        for (Field field : variables) {
//            System.out.println(field.getName());
//        }

        List<Map<String, Object>> content = boardRepository.selectPageableBoardList(requestList);
        int total = boardRepository.selectListBoardCount(tbBoard);
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    @Transactional
    public Map<String, Object> insertBoard(String menuCd, TbBoard board, MultipartFile[] files) {
        Map<String, Object> result = new HashMap<String, Object>();
        board.setMenuCd(menuCd);
        board.setMenuDvCd(menuRepository.selectMenuByMenuCd(menuCd).getTmplCd());
        if (board.getPassword().isEmpty()) {
            board.setUseYn("N");
        } else {
            board.setUseYn("Y");
        }
        board.setDelYn("N");
        board.setRplyYn("N");
        if (files != null) {
            board.setFileYn("Y");
        } else {
            board.setFileYn("N");
        }

        board.setViewCount("0");
        board.setRegId(SecurityUtils.getPrincipal().getUserId());
        board.setModId(SecurityUtils.getPrincipal().getUserId());

        boardRepository.insertBoard(board);

        if (files != null) uploadFiles(board, files);

        result.put("result", "success");

        return result;
    }

    @Override
    public Map<String, Object> updateBoard(String menuCd, TbBoard board, MultipartFile[] files) {
        Map<String, Object> result = new HashMap<String, Object>();

        board.setMenuCd(menuCd);
        board.setModId(SecurityUtils.getPrincipal().getUserId());
        if (files != null) {
            board.setFileYn("Y");
        } else {
            board.setFileYn("N");
        }

        if (board.getPassword().isEmpty()) {
            board.setUseYn("N");
        } else {
            board.setUseYn("Y");
        }

        boardRepository.updateBoard(board);

        uploadFiles(board, files);

        result.put("result", "success");

        return result;
    }

    @Override
    @Transactional
    public void uploadFiles(TbBoard board, MultipartFile[] files) {
        TbFile inputFile = new TbFile();
        inputFile.setPkId(board.getBoardId());
        inputFile.setMenuCd(board.getMenuCd());
        // boardId 해당 게시물의 파일을 모두 삭제하고 다시 추가
        fileRepository.deleteFiles(inputFile);

        File uploadPath = new File(FILE_UPLOAD_PATH + "/board", StringUtils.getData());

        System.out.println("upload path: " + uploadPath);

        if (uploadPath.exists() == false) {
            uploadPath.mkdirs();
        }

        if (board.getJsonFileList() != null) {
            System.out.println("file ::: 기존파일추가  --------------------------");

            // List<String>의 형태를 List<TbFile>로 변환
            ObjectMapper mapper = new ObjectMapper();
            List<TbFile> fileList = new ArrayList<>();

            for (String jsonFile : board.getJsonFileList()) {

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

        if (files != null) {
            System.out.println("file ::: 새 파일 추가 --------------------------");
            // 새로운 파일 DB추가
            for (MultipartFile file : files) {
                String oriFileName = file.getOriginalFilename();

                UUID uuid = UUID.randomUUID(); // 랜덤 이름 생성

                String uploadFileName = uuid.toString() + "_" + oriFileName; //UUID(랜덤문자라생각하면편함) + 원본파일명

                File saveFile = new File(uploadPath, uploadFileName);

                TbFile tbFile = new TbFile();
                tbFile.setPkId(board.getBoardId());
                tbFile.setMenuCd(board.getMenuCd());
                tbFile.setFileName(oriFileName);
                tbFile.setFileNewName(uploadFileName);
                tbFile.setFileSize(file.getSize() + "");
                tbFile.setFilePath(uploadPath + "");
                // 01 : 서버
                tbFile.setFileType("01");
                tbFile.setFileExtension(StringUtils.getFileExtension(oriFileName));
                tbFile.setRegId(SecurityUtils.getPrincipal().getRegId());

                try {
                    file.transferTo(saveFile); //물리적인 파일을 해당경로에 저장한다.

                    fileRepository.insertFiles(tbFile);
                } catch (Exception e) {
                    // log.error(e.getMessage());
                    // log.error("error : ",e);
                }
            }
        }
    }

    @Override
    public ResponseEntity<Resource> downloadFiles(String menuCd, String boardId, String fileId) {

        TbFile reqfile = new TbFile();
        reqfile.setFileId(fileId);
        reqfile.setPkId(boardId);
        reqfile.setMenuCd(menuCd);

        TbFile resFile = fileRepository.selectFile(reqfile);

        try {
            String fileName = resFile.getFileName();
            String fileNewName = resFile.getFileNewName();

            String encodedFilename = URLEncoder.encode(fileName, "UTF-8");

            Path filePath = Paths.get(resFile.getFilePath()).resolve(fileNewName).normalize();

            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFilename + "\"")
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }


    @Override
    public TbBoard selectBoardDetail(String menuCd, String boardId, HttpServletRequest request, HttpServletResponse response) {
        TbBoard board = new TbBoard();
        board.setBoardId(boardId);
        board.setMenuCd(menuCd);
        board.setModId(SecurityUtils.getPrincipal().getUserId());
        board.setViewCount("set");
        // 조회수 카운트
        this.viewCount(board, request, response);

        return boardRepository.selectBoardDetail(menuCd, boardId);
    }

    @Override
    public List<TbFile> selectBoardFiles(String menuCd, String boardId) {

        TbFile tbFile = new TbFile();
        tbFile.setMenuCd(menuCd);
        tbFile.setPkId(boardId);

        return fileRepository.selectFileList(tbFile);
    }

    @Override
    public Map<String, Object> deleteBoard(TbBoard board) {
        Map<String, Object> result = new HashMap<String, Object>();

        board.setModId(SecurityUtils.getPrincipal().getUserId());
        board.setDelYn("Y");
        boardRepository.updateBoard(board);

        result.put("result", "success");

        return result;
    }

    private void viewCount(TbBoard board, HttpServletRequest request, HttpServletResponse response) {
        Cookie oldCookie = null;

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("postView")) {
                    oldCookie = cookie;
                }
            }
        }

        // oldCookie에 값이 있다면, 즉 postView가 존재한다면
        // 해당 쿠키의 value가 현재 접근한 게시글의 번호(bbsNum)를 포함하고 있는지 검사한다.
        if (oldCookie != null) {
            // 단순히 숫자만 사용하면 문제가 생길 수 있어서 괄호로 감싸 숫자를 온전히 검사하고자 함
            if (!oldCookie.getValue().contains("[" + board.getBoardId() + "]")) {
                boardRepository.updateBoard(board);
                oldCookie.setValue(oldCookie.getValue() + "_[" + board.getBoardId() + "]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(1800);
                response.addCookie(oldCookie);
            }
            // null이면 DB조회수 올리고
            // 해당 게시글 id를 괄호로 감싼 새로운 쿠키 postView를 생성하여 HttpServletResponse에게 전달한다.
        } else {
            boardRepository.updateBoard(board);

            Cookie newCookie = new Cookie("postView", "[" + board.getBoardId() + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(1800);
            response.addCookie(newCookie);
        }

    }
}
