package com.mrc.db.dto.user;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserBannerList implements Serializable{
	private Integer memberCode;
	private String memberName;
	private Integer bannerCode;
	private String title;
	private String subTitle;
	private Integer bannerType;
	private String content;
	private String thumbImageUrl;
	private Integer searchCnt;
	private Integer diffMin;
	private String areaData;
	private Integer maxGate;
	private String localboxName;
	private String profileUrl;
	private Boolean isBookmark;
	private Integer bookmarkCode;
}
