package com.mrc.db.dto.user;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAlamMsgData implements Serializable{
	private Integer rowNum;
	/*1 배너 공유 2 배너북마크 3 로컬박스(로컬러) 북마크  */
	private Integer alamType;
	/**
	 * 배너공유 alam_type =1  request_code 2 배너 3 로컬박스 bookmark_code
	 * */
	private Integer code;
	
	/**
	 * 배너공유 alam_type =1  2 : a.bookmark_type = 1 bookmark_code a.bookmark_type = 2 localbox_code
	 * */
	private Integer code2;
	private String title;
	private String content;
	/**
	 *  날짜  차이
	 */
	private Integer diffDays;
	/**
	 * 등록자코드
	 */
	private Integer memberCode;
	/**
	 * � 등록자
	 */
	private String memberName;
	/**
	 * � 등록일(문자)
	 */
	private String regDate2;
	/**
	 * �등록시간
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp regDate;
	
}
