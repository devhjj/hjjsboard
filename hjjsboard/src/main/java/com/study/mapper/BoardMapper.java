package com.study.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.study.domain.BoardDto;

@Mapper
public interface BoardMapper {

	public int insertBoard(BoardDto params);

	public BoardDto selectBoardDetail(Long seq);

	public int updateBoard(BoardDto params);

	public int deleteBoard(Long seq);

	public List<BoardDto> selectBoardList(BoardDto params);	//paging을 위해 BoardDto에 criteria 상속한 CommonDto 상속.

	public int selectBoardTotalCount(BoardDto params);	//paging을 위해 BoardDto에 criteria 상속한 CommonDto 상속.
	
	public int updateViewCnt(Long seq);
	
	public int insertIP(Map<String,String> map);
	
	public int updateIP(Map<String,String> map);
	
	public int checkIP(Map<String,String> map);
	
	public int todayIP(Map<String,String> map);
	
	public int viewTotal(int seq);
	
	public int viewToday(int seq);

}