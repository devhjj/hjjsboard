package com.study.service;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.domain.CommentDto;
import com.study.mapper.CommentMapper;
@Service
public class CommentServiceImpl implements CommentService{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private CommentMapper commentMapper;
	
	@Override
	public boolean registerComment(CommentDto params) {
		// TODO Auto-generated method stub
		int queryResult = 0;
		
		if(params.getSeq() == null){
			queryResult = commentMapper.insertComment(params);
		}else{
			if("D".equals(params.getStatus())) {
				queryResult = commentMapper.deleteComment(params.getSeq());
			}else {
				queryResult = commentMapper.updateComment(params);
			}
			
		}
		logger.debug("params:", params.getSeq()+" "+params.getStatus());
		
		return (queryResult==1) ? true : false ;
	}

	@Override
	public boolean deleteComment(Long seq) {
		// TODO Auto-generated method stub
		int queryResult = 0;
		
		CommentDto commentDto = commentMapper.selectCommentDetail(seq);
		
		if(commentDto != null && !"D".equals(commentDto.getStatus())) {
			queryResult = commentMapper.deleteComment(seq);
		}
		
		return (queryResult==1) ? true : false;
	}

	@Override
	public List<CommentDto> getCommentList(CommentDto params) {
		// TODO Auto-generated method stub
		List<CommentDto> commentList = Collections.emptyList();

		int commentTotalCount = commentMapper.selectCommentTotalCount(params);
		if (commentTotalCount > 0) {
			commentList = commentMapper.selectCommentList(params);
		}

		return commentList;
	}

}
