package com.ncookie.springpractice.board.service;

import com.ncookie.springpractice.board.dto.BoardDto;
import com.ncookie.springpractice.board.entity.BoardEntity;
import com.ncookie.springpractice.board.repository.BoardRepository;
import com.ncookie.springpractice.util.PageVo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BoardService {
    private BoardRepository boardRepository;

    private static final int PAGE_POST_COUNT = 10;       // 한 페이지에 존재하는 게시글 수

    @Transactional
    public Long savePost(BoardDto boardDto) {
        return boardRepository.save(boardDto.toEntity()).getId();
    }

    @Transactional
    public Page<BoardDto> getBoardPageList(int pageNo) {

        Pageable pageable = PageRequest.of(pageNo, PAGE_POST_COUNT, Sort.by(Sort.Direction.ASC, "id"));

        Page<BoardEntity> page = boardRepository.findAll(pageable);
        return page.map(BoardDto::from);
    }

    public PageVo getPageInfo(Page<BoardDto> boardPageList, int pageNo) {
        int totalPage = boardPageList.getTotalPages();

        // 현재 페이지를 통해 현재 페이지 그룹의 시작 페이지를 구함
        int startNumber = (int)((Math.floor((double) pageNo / PAGE_POST_COUNT) * PAGE_POST_COUNT) + 1 <= totalPage
                ? (Math.floor((double) pageNo / PAGE_POST_COUNT) * PAGE_POST_COUNT) + 1 : totalPage);

        // 전체 페이지 수와 현재 페이지 그룹의 시작 페이지를 통해 현재 페이지 그룹의 마지막 페이지를 구함
        int endNumber = (Math.min(startNumber + PAGE_POST_COUNT - 1, totalPage));
        boolean hasPrev = boardPageList.hasPrevious();
        boolean hasNext = boardPageList.hasNext();

        // 프론트에서는 원래 페이지의 인덱스 + 1로 출력됨
        int prevIndex = boardPageList.previousOrFirstPageable().getPageNumber() + 1;
        int nextIndex = boardPageList.nextOrLastPageable().getPageNumber() + 1;

        return new PageVo(totalPage, startNumber, endNumber, hasPrev, hasNext, prevIndex, nextIndex);
    }

    @Transactional
    public BoardDto getPost(Long id) {
        Optional<BoardEntity> boardEntityWrapper = boardRepository.findById(id);
        BoardEntity boardEntity = boardEntityWrapper.get();

        return BoardDto.from(boardEntity);
    }

    @Transactional
    public void deletePost(Long id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public List<BoardDto> searchPosts(String keyword) {
        List<BoardEntity> boardEntities = boardRepository.findByTitleContaining(keyword);
        List<BoardDto> boardDtoList = new ArrayList<>();

        if (boardEntities.isEmpty()) {
            return boardDtoList;
        }

        for (BoardEntity boardEntity : boardEntities) {
            boardDtoList.add(BoardDto.from(boardEntity));
        }

        return boardDtoList;
    }
}
