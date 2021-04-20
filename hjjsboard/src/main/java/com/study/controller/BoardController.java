package com.study.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import com.study.adapter.GsonLocalDateTimeAdapter;
import com.study.domain.BoardDto;
import com.study.service.BoardService;
import com.study.constant.Method;
import com.study.util.UiUtils;

@Controller
public class BoardController extends UiUtils {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BoardService boardService;
	
	public String clientIp() {
		HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String ip = req.getHeader("X-FORWARDED-FOR");
		if (ip == null) {
			ip = req.getRemoteAddr();
		}
		return ip;
	}
	
	// 게시물 등록화면
	@GetMapping(value = "/board/write.do")
	public String setBoardWrite(@ModelAttribute("params") BoardDto params, @RequestParam(value = "seq", required = false) Long seq, Model model) {
		if(seq == null) {
			model.addAttribute("board", new BoardDto());
		} else {
			BoardDto boardDto = boardService.getBoardDetail(seq);
			if(boardDto == null || "D".equals(boardDto.getStatus())) {
				/*return "redirect:/";*/
				return showMessageWithRedirect("없는 게시글이거나 이미 삭제된 게시글입니다.", "/board/list.do", Method.GET, null, model);
			}
			model.addAttribute("board", boardDto);
		}
		return "board/write";
	}
	
	@GetMapping(value = "/board/list.do")
	public String getBoardList(@ModelAttribute("params") BoardDto params, Model model) {
		List<BoardDto> list = boardService.getBoardList(params);
		model.addAttribute("boardList", list);
		
		model.addAttribute("todayView",params.getTodayView());
		model.addAttribute("totalView",params.getTotalView());
		model.addAttribute("clientIP", clientIp());
		return "board/list";
	}
	
	@PostMapping(value = "/board/register.do")
	public String setBoardRegister(@ModelAttribute("params") final BoardDto params, Model model) {
		Map<String, Object> pagingParams = getPagingParams(params);
		try {
			boolean isRegistered = boardService.setBoardRegister(params);
			if (isRegistered == false) {
				return showMessageWithRedirect("게시글 등록에 실패하였습니다.", "/board/list.do", Method.GET, pagingParams, model);
			}
		} catch (DataAccessException e) {
			return showMessageWithRedirect("DB 처리 과정에 문제가 발생하였습니다.", "/board/list.do", Method.GET, pagingParams, model);

		} catch (Exception e) {
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/board/list.do", Method.GET, pagingParams, model);
		}

		return showMessageWithRedirect("게시글이 등록되었습니다.", "/board/list.do", Method.GET, pagingParams, model);
	}
	
	@GetMapping(value = "/board/view.do")
	public String getBoardDetail(@ModelAttribute("params") BoardDto params, @RequestParam(value = "seq", required = false) Long seq, Model model) {
		if (seq == null) {
			// TODO => 올바르지 않은 접근이라는 메시지를 전달하고, 게시글 리스트로 리다이렉트
			/* return "redirect:/"; */
			return showMessageWithRedirect("올바르지 않은 접근입니다.", "/board/list.do", Method.GET, null, model);
		}

		BoardDto boardDto = boardService.getBoardDetail(seq);
		if (boardDto == null || "D".equals(boardDto.getStatus())) {
			// TODO => 없는 게시글이거나, 이미 삭제된 게시글이라는 메시지를 전달하고, 게시글 리스트로 리다이렉트
			/* return "redirect:/"; */
			return showMessageWithRedirect("없는 게시글이거나 이미 삭제된 게시글입니다.", "/board/list.do", Method.GET, null, model);
		}
		model.addAttribute("board", boardDto);
		model.addAttribute("clientIP", clientIp());

		return "board/view";
	}
	
	@PostMapping(value = "/board/delete.do")
	public String setBoardDelete(@ModelAttribute("params") BoardDto params, @RequestParam(value = "seq", required = false) Long seq, Model model) {
		if (seq == null) {
			return showMessageWithRedirect("올바르지 않은 접근입니다.", "/board/list.do", Method.GET, null, model);
		}

		Map<String, Object> pagingParams = getPagingParams(params);
		try {
			boolean isDeleted = boardService.setBoardDelete(seq);
			if (isDeleted == false) {
				return showMessageWithRedirect("게시글 삭제에 실패하였습니다.", "/board/list.do", Method.GET, pagingParams, model);
			}
		} catch (DataAccessException e) {
			return showMessageWithRedirect("DB 처리 과정에 문제가 발생하였습니다.", "/board/list.do", Method.GET, pagingParams, model);

		} catch (Exception e) {
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/board/list.do", Method.GET, pagingParams, model);
		}

		return showMessageWithRedirect("게시글이 삭제되었습니다.", "/board/list.do", Method.GET, pagingParams, model);
	}
	
}