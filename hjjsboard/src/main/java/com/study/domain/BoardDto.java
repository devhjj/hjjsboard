package com.study.domain;

import com.study.domain.CommonDto;

public class BoardDto extends CommonDto {

	private String title;
	private String content;
	private Long seq;
	private int view_cnt;
	private int comment_cnt;
	private String totalView;
	private String todayView;
	private String img_file;
	
	public String getImg_file() {
		return img_file;
	}
	public void setImg_file(String img_file) {
		this.img_file = img_file;
	}
	public String getTotalView() {
		return totalView;
	}
	public void setTotalView(String string) {
		this.totalView = string;
	}
	public String getTodayView() {
		return todayView;
	}
	public void setTodayView(String todayView) {
		this.todayView = todayView;
	}
	public int getComment_cnt() {
		return comment_cnt;
	}
	public void setComment_cnt(int comment_cnt) {
		this.comment_cnt = comment_cnt;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
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
	public int getView_cnt() {
		return view_cnt;
	}
	public void setView_cnt(int view_cnt) {
		this.view_cnt = view_cnt;
	}
	
	
}