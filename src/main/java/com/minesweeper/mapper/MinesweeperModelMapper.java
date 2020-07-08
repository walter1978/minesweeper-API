package com.minesweeper.mapper;

import com.minesweeper.dto.BoardDTO;
import com.minesweeper.dto.BoardStatusDTO;
import com.minesweeper.model.Board;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MinesweeperModelMapper extends ModelMapper {
    public BoardDTO mapToBoardDTO(Board board) {
        return map(board, BoardDTO.class);
    }

    public List<BoardStatusDTO> mapToBoardStatusDTOs(List<Board> boards) {
        final List<BoardStatusDTO> boardStatusDTOList = new ArrayList<>();
        for (Board board : boards) {
            boardStatusDTOList.add(mapToBoarStatusDTO(board));
        }
        return boardStatusDTOList;
    }

    public BoardStatusDTO mapToBoarStatusDTO(Board board) {
        return map(board, BoardStatusDTO.class);
    }
}
