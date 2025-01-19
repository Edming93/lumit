package com.lumit.shop.common.controller.restController;

import com.lumit.shop.board.service.BoardService;
import com.lumit.shop.common.dto.SearchDto;
import com.lumit.shop.common.model.TbBoard;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class BoardRestController {
    private final BoardService boardService;

    @GetMapping(value = "/boards")
    public @ResponseBody ResponseEntity<?> boardList(String menuCd, SearchDto search, TbBoard board, @PageableDefault(size = 10) Pageable pageable) throws IOException {
        board.setMenuCd(menuCd);
        if (search.getTitle() != null) {
            board.setTitle(search.getTitle());
        }

        if (search.getCategories() != null) {
            board.setCategories(search.getCategories());
        }
        return ResponseEntity.ok(boardService.selectPageableBoardList(board, pageable));
    }

}
