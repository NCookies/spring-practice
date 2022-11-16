package com.ncookie.springpractice.board.repository;

import com.ncookie.springpractice.board.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    List<BoardEntity> findByTitleContaining(String keyword);
}