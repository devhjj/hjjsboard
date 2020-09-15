package com.study.domain;

import java.time.LocalDateTime;

import com.study.paging.Criteria;
import com.study.paging.PaginationInfo;

public class CommonDto extends Criteria {

	/** 페이징 정보 */
	private PaginationInfo paginationInfo;

	/** 삭제 여부 */
	private String status;

	/** 등록일 */
	private LocalDateTime reg_date; 

	/** 수정일 */
	private LocalDateTime mod_date;
	
	/* ip 관련*/
	private String ip;
	
	private LocalDateTime ip_reg_date;
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public LocalDateTime getIp_reg_date() {
		return ip_reg_date;
	}

	public void setIp_reg_date(LocalDateTime ip_reg_date) {
		this.ip_reg_date = ip_reg_date;
	}

	public PaginationInfo getPaginationInfo() {
		return paginationInfo;
	}

	public void setPaginationInfo(PaginationInfo paginationInfo) {
		this.paginationInfo = paginationInfo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getReg_date() {
		return reg_date;
	}

	public void setReg_date(LocalDateTime reg_date) {
		this.reg_date = reg_date;
	}

	public LocalDateTime getMod_date() {
		return mod_date;
	}

	public void setMod_date(LocalDateTime mod_date) {
		this.mod_date = mod_date;
	}
}
