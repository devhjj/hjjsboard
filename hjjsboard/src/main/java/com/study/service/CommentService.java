package com.study.service;

import java.util.List;

import com.study.domain.CommentDto;

public interface CommentService {
	public boolean registerComment(CommentDto params);

	public boolean deleteComment(Long seq);

	public List<CommentDto> getCommentList(CommentDto params);
}
