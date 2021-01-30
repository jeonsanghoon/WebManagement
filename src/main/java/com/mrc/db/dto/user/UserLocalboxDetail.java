package com.mrc.db.dto.user;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLocalboxDetail implements Serializable{
	private Integer memberCode;
	private String memberName;
	private Integer authType;
	private Integer localboxCode;
	private String localboxName;
	private String phone;
	private String profileUrl;
	private String desc;
	private String announce;
	private Integer bookmarkCode;
	private Boolean isBookmark;
	private Integer bookmarkCnt;
	private String businessTime;
	private Integer businessType;
	private String businessTypeName;
	private Integer frame1Cnt;
	private Integer frame6Cnt;
	private String address;
	private String roadAddress;
	private String addressDetail;
	@Default
	private String areaCode="삼성동";
	@Default
	private String areaName="01|01|17";
}
