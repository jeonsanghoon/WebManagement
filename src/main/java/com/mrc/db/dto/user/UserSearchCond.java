package com.mrc.db.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@SuppressWarnings("serial")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSearchCond implements Serializable{
	private Integer searchCode;
	
	private Double latitude;
	private Double longitude;
	private String areaCode;
	private String areaName;
	private Integer keywordCode;
	private String keywordName;
	private String currentArea;
	@Default
	private Integer memberCode = -1;
	private Integer diffMin;
	private Integer searchCnt;
	@Default
	private Integer addSearchCnt = 1;
	private String orderBy;
	private Boolean isBookmark;
}
