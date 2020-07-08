package com.minesweeper.dao;

import com.minesweeper.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardDAO extends JpaRepository<Board, Long> {
    List<Board> findByUserId(Long userId);
}
