package com.lumit.shop.board.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lumit.shop.board.service.BoardService;
import com.lumit.shop.common.model.TbBoard;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/{siteId}/board")
public class BoardController {
	private final BoardService boardService;
    private final String BOARD_PATH = "/board";


    @GetMapping("/{menuCd}/list")
    public String selectBoardList(ModelMap map, HttpServletRequest request, @PathVariable("menuCd") String menuCd) {
    	map.addAttribute("menuCd", menuCd);
    	map.addAttribute("request", request);
    	map.addAttribute("boardList",boardService.selectBoardList());
        return BOARD_PATH + "/list";
    }

    @GetMapping("/{menuCd}/regist")
    public String boardRegist(ModelMap map, HttpServletRequest request, @PathVariable("menuCd") String menuCd) {
    	map.addAttribute("menuCd", menuCd);
    	map.addAttribute("request", request);
        return BOARD_PATH + "/regist";
    }
    
    @ResponseBody
    @PostMapping("/{menuCd}/regist")
    public  ResponseEntity<Map<String,Object>> insertBoard(ModelMap map, @PathVariable("menuCd") String menuCd, @RequestBody TbBoard board) {
    	return new ResponseEntity<>(boardService.insertBoard(menuCd,board),HttpStatus.OK);
    }


} 
