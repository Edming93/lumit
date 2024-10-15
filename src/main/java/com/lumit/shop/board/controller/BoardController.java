package com.lumit.shop.board.controller;

import java.util.Map;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.lumit.shop.board.service.BoardService;
import com.lumit.shop.common.dto.SearchDto;
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
    public String selectBoardList(ModelMap map, HttpServletRequest request, @PathVariable("menuCd") String menuCd, SearchDto search) {
        map.addAttribute("menuCd", menuCd);
        map.addAttribute("title", search.getTitle());
        map.addAttribute("boardList", boardService.selectBoardList(search));

        return BOARD_PATH + "/list";
    }

    @GetMapping("/{menuCd}/pageableList")
    public String selectPageableBoardList(ModelMap map, @PathVariable("menuCd") String menuCd, @RequestParam(required = false) String page) {
        map.addAttribute("menuCd", menuCd);
        return page.isEmpty() ? BOARD_PATH + "/listPageable" : BOARD_PATH + "/listPageable?page=" + page;
    }

    @GetMapping("/{menuCd}/regist")
    public String boardRegist(ModelMap map, HttpServletRequest request, @PathVariable("menuCd") String menuCd) {
        map.addAttribute("menuCd", menuCd);
        return BOARD_PATH + "/regist";
    }

    @GetMapping("{menuCd}/detail/{boardId}")
    public String boardDetail(ModelMap map, HttpServletRequest request, HttpServletResponse response, @PathVariable("menuCd") String menuCd, @PathVariable("boardId") String boardId) {
        map.addAttribute("detail", boardService.selectBoardDetail(menuCd, boardId, request, response));
        map.addAttribute("menuCd", menuCd);
        return BOARD_PATH + "/detail";
    }

    @ResponseBody
    @PostMapping("/{menuCd}/regist")
    public ResponseEntity<Map<String, Object>> insertBoard(ModelMap map, @PathVariable("menuCd") String menuCd, @RequestBody TbBoard board) {
        return new ResponseEntity<>(boardService.insertBoard(menuCd, board), HttpStatus.OK);
    }

    @GetMapping("/{menuCd}/update/{boardId}")
    public String boardUpdate(ModelMap map, HttpServletRequest request, HttpServletResponse response, @PathVariable("menuCd") String menuCd, @PathVariable("boardId") String boardId) {
        map.addAttribute("detail", boardService.selectBoardDetail(menuCd, boardId, request, response));
        return BOARD_PATH + "/update";
    }

    @ResponseBody
    @PostMapping("/{menuCd}/update")
    public ResponseEntity<Map<String, Object>> updateBoard(ModelMap map, @PathVariable("menuCd") String menuCd, @RequestBody TbBoard board) {
        return new ResponseEntity<>(boardService.updateBoard(menuCd, board), HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/{menuCd}/delete")
    public ResponseEntity<Map<String, Object>> deleteBoard(ModelMap map, @PathVariable("menuCd") String menuCd, @RequestBody TbBoard board) {
        return new ResponseEntity<>(boardService.deleteBoard(board), HttpStatus.OK);
    }

} 
