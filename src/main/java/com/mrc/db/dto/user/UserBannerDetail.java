package com.mrc.db.dto.user;

import java.io.Serializable;
import java.util.List;

import com.mrc.db.dto.banner.BannerSub;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserBannerDetail implements Serializable{
	private Integer bannerCode;
	private Integer bannerType;
	private String title;
	private String subTitle;
	private String content;
	private Integer playType;
	private	String bannerImageUrl;
	private String bannerMovieUrl;
	private Integer memberCode;
	private String memberName;
	private Integer bookmarkCode;
	private Boolean isBookmark;
	private Integer diffMin;
	private Integer bookmarkCnt;
	private Integer searchCnt;
	private String localboxName;
	private Double latitude;
	private Double longitude;
	private String address;
	private String keywords;
	private String profileUrl;
	private String areaCode;
	private String areaName;
	private List<BannerSub> subList;
}
