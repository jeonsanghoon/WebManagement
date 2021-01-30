package com.mrc.db.dto.user;

import java.io.Serializable;

import org.apache.poi.hpsf.Decimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLocalboxList implements Serializable{
	private Integer bannerCode;
	private Integer memberCode;
	private String memberName;
	private String name;
	private String desc;
	private String profileUrl;
	private String address;
	private String areaAddress;
	private Integer localboxCode;
	private Decimal distance;
	private Boolean isBookmark;
	private Integer bookmarkCode;
}
