package com.study.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.study.domain.CommentDto;

@Mapper
public interface CommentMapper {

	public int insertComment(CommentDto params);

	public CommentDto selectCommentDetail(Long seq);

	public int updateComment(CommentDto params);

	public int deleteComment(Long seq);

	public List<CommentDto> selectCommentList(CommentDto params);

	public int selectCommentTotalCount(CommentDto params);

}