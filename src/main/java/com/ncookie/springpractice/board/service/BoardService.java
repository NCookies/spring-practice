package com.ncookie.springpractice.board.service;

import com.ncookie.springpractice.board.dto.BoardDto;
import com.ncookie.springpractice.board.repository.BoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class BoardService {
    private BoardRepository boardRepository;

    @Transactional
    public Long savePost(BoardDto boardDto) {
        return boardRepository.save(boardDto.toEntity()).getId();
    }
}
