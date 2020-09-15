package com.study.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.study.domain.BoardDto;
import com.study.mapper.BoardMapper;
import com.study.paging.PaginationInfo;

@Service
public class BoardServiceImpl implements BoardService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BoardMapper boardMapper;
	
	// 게시물 등록, 수정
	@Override
	public boolean setBoardRegister(BoardDto params) {
		int queryResult = 0;

		if (params.getSeq() == null) {
			queryResult = boardMapper.insertBoard(params);
		} else {
			queryResult = boardMapper.updateBoard(params);
		}
		
		logger.debug("setBoardRegister_queryResult==0"+ queryResult);

		return (queryResult == 1) ? true : false;
	}

	// 게시물 상세
	@Override
	public BoardDto getBoardDetail(Long seq) {
		viewCount(seq);

		return boardMapper.selectBoardDetail(seq);
	}

	// 게시물 삭제
	@Override
	public boolean setBoardDelete(Long seq) {
		int queryResult = 0;

		BoardDto board = boardMapper.selectBoardDetail(seq);

		if (board != null && !("D".equals(board.getStatus()))) {
			queryResult = boardMapper.deleteBoard(seq);
		}

		return (queryResult == 1) ? true : false;
	}

	// 게시물 리스트
	@Override
	public List<BoardDto> getBoardList(BoardDto params) {	// BoardDto에 criteria 상속한 CommonDto 상속.
		Map<String, String> vc = viewCount(0);
		
		params.setTotalView(vc.get("viewTotal"));
		params.setTodayView(vc.get("viewToday"));
		
		List<BoardDto> boardList = Collections.emptyList();
		
		int boardTotalCount = boardMapper.selectBoardTotalCount(params);
		
		PaginationInfo paginationInfo = new PaginationInfo(params);
		paginationInfo.setTotalRecordCount(boardTotalCount);

		params.setPaginationInfo(paginationInfo);

		if (boardTotalCount > 0) {
			boardList = boardMapper.selectBoardList(params);
		}

		return boardList;
	}
	
	// check view count
	public Map<String, String> viewCount(long seq) {
		Map<String, String> result = new HashMap<String, String>();
		HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String ip = req.getHeader("X-FORWARDED-FOR");
		if (ip == null) {
			ip = req.getRemoteAddr();
		}
		Map<String, String> map = new HashMap<String, String>();
		// get client ip address
		int ip_chk;
		int date_chk;
		map.put("seq", String.valueOf(seq));
		map.put("ip", ip);
		
		// get ip
		ip_chk = boardMapper.checkIP(map);
		date_chk = boardMapper.todayIP(map);
		
		if(ip_chk == 0) {
			// set ip
			boardMapper.insertIP(map);
			// update view count
			boardMapper.updateViewCnt(seq);
		}else if(date_chk == 0) {
			// set ip
			boardMapper.updateIP(map);
			// update view count
			boardMapper.updateViewCnt(seq);
		}
		result.put("viewTotal",String.valueOf(boardMapper.viewTotal(0)));
		result.put("viewToday",String.valueOf(boardMapper.viewToday(0)));
		  
		return result;
	}
	
	// 게시물 리스트
	@Override
	public List<BoardDto> getDodamBoardList(BoardDto params) {	// BoardDto에 criteria 상속한 CommonDto 상속.
		viewCount(0);
		
		List<BoardDto> boardList = Collections.emptyList();
		
		int boardTotalCount = boardMapper.selectBoardTotalCount(params);
		
		PaginationInfo paginationInfo = new PaginationInfo(params);
		paginationInfo.setTotalRecordCount(boardTotalCount);

		params.setPaginationInfo(paginationInfo);

		if (boardTotalCount > 0) {
			boardList = boardMapper.selectBoardList(params);
		}

		return boardList;
	}
}