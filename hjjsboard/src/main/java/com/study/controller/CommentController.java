package com.study.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import com.study.adapter.GsonLocalDateTimeAdapter;
import com.study.domain.CommentDto;
import com.study.service.CommentService;

@RestController
public class CommentController {

	@Autowired
	private CommentService commentService;

	@GetMapping(value = "/comments_old/{boardSeq}")
	public JsonObject getCommentList_old(@PathVariable("boardSeq") Long boardSeq, @ModelAttribute("params") CommentDto params) {

		JsonObject jsonObj = new JsonObject();

		List<CommentDto> commentList = commentService.getCommentList(params);
		if (CollectionUtils.isEmpty(commentList) == false) {
			JsonArray jsonArr = new Gson().toJsonTree(commentList).getAsJsonArray();
			jsonObj.add("commentList", jsonArr);
		}

		return jsonObj;
	}
	
	@GetMapping(value = "/comments/{boardSeq}")
	public JsonObject getCommentList(@PathVariable("boardSeq") Long boardSeq, @ModelAttribute("params") CommentDto params) {

		JsonObject jsonObj = new JsonObject();

		List<CommentDto> commentList = commentService.getCommentList(params);
		if (CollectionUtils.isEmpty(commentList) == false) {
			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter()).create();
			JsonArray jsonArr = gson.toJsonTree(commentList).getAsJsonArray();
			jsonObj.add("commentList", jsonArr);
		}

		return jsonObj;
	}
	
	@RequestMapping(value = { "/comments", "/comments/{seq}" }, method = { RequestMethod.POST, RequestMethod.PATCH })
	public JsonObject registerComment(@PathVariable(value = "seq", required = false) Long seq, @RequestBody final CommentDto params) {

		JsonObject jsonObj = new JsonObject();

		try {
			if (seq != null) {
				params.setSeq(seq);
			}

			boolean isRegistered = commentService.registerComment(params);
			jsonObj.addProperty("result", isRegistered);

		} catch (DataAccessException e) {
			jsonObj.addProperty("message", "DB 처리 과정에 문제가 발생하였습니다.");

		} catch (Exception e) {
			jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
		}

		return jsonObj;
	}

}
