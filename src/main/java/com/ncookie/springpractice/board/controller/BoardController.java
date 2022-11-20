package com.ncookie.springpractice.board.controller;

import com.ncookie.springpractice.board.dto.BoardDto;
import com.ncookie.springpractice.board.entity.BoardEntity;
import com.ncookie.springpractice.board.service.BoardService;
import com.ncookie.springpractice.util.PageVo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor     // 모든 필드 값을 사용하는 생성자를 만들어주는 어노테이션
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/test")
    public String addTestData() {
        for (int i = 0; i < 300; i++) {
            boardService.savePost(BoardDto.from(new BoardEntity(
                    (long) (i + 1),
                    "테스트" + (i + 1),
                    "테스터 " + (i + 1),
                    "test"
            )));
        }

        return "redirect:/";
    }

    @GetMapping("/")
    public String readAllPost(Model model, @RequestParam(value = "page", defaultValue = "1") Integer pageNum) {
        int pageNo = (pageNum == 0) ? 0 : pageNum - 1;

        Page<BoardDto> boardList = boardService.getBoardPageList(pageNo);
        PageVo pageVo = boardService.getPageInfo(boardList, pageNo);

        model.addAttribute("boardList", boardList);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("pageVo", pageVo);

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

    @GetMapping("/post/{no}")
    public String detail(@PathVariable("no") Long no, Model model) {
        BoardDto boardDto = boardService.getPost(no);

        model.addAttribute("boardDto", boardDto);
        return "board/detail";
    }

    @GetMapping("/post/edit/{no}")
    public String edit(@PathVariable("no") Long no, Model model) {
        BoardDto boardDto = boardService.getPost(no);

        model.addAttribute("boardDto", boardDto);
        return "board/update";
    }

    @PutMapping("/post/edit/{no}")
    public String update(BoardDto boardDto) {
        boardService.savePost(boardDto);

        return "redirect:/";
    }

    @DeleteMapping("/post/{no}")
    public String delete(@PathVariable("no") Long no) {
        boardService.deletePost(no);

        return "redirect:/";
    }

    @GetMapping("/board/search")
    public String search(@RequestParam(value = "keyword") String keyword, Model model) {
        List<BoardDto> boardDtoList = boardService.searchPosts(keyword);

        model.addAttribute("boardList", boardDtoList);

        return "board/list";
    }
}
