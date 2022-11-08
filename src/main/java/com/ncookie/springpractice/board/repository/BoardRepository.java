package com.ncookie.springpractice.board.repository;

import com.ncookie.springpractice.board.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
}