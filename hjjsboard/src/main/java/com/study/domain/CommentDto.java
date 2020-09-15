package com.study.domain;

import com.study.domain.CommonDto;

public class CommentDto extends CommonDto {

	private String content;
	private String writer;
	private Long seq;
	private Long boardSeq;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getSeq() {
		return seq;
	}
	public void setSeq(Long seq) {
		this.seq = seq;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public Long getBoardSeq() {
		return boardSeq;
	}
	public void setBoardSeq(Long boardSeq) {
		this.boardSeq = boardSeq;
	}
	
}