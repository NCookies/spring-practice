package com.ncookie.springpractice.board.controller;

import com.ncookie.springpractice.board.dto.BoardDto;
import com.ncookie.springpractice.board.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor     // 모든 필드 값을 사용하는 생성자를 만들어주는 어노테이션
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/")
    public String list() {
        return "board/list";
    }

    @GetMapping("/post")
    public String write() {
        return "board/write";
    }

    @PostMapping("/post")
    public String write(BoardDto boardDto) {
        boardService.savePost(boardDto);

        return "redirect:/";
    }
}

