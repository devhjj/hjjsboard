package com.study.service;

import java.util.List;

import com.study.domain.BoardDto;

public interface BoardService {

	public boolean setBoardRegister(BoardDto params);

	public BoardDto getBoardDetail(Long seq);

	public boolean setBoardDelete(Long seq);

	public List<BoardDto> getBoardList(BoardDto params);	// BoardDto에 criteria 상속한 CommonDto 상속.

	public List<BoardDto> getDodamBoardList(BoardDto params);
}
