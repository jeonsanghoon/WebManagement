package com.mrc.db.dto.mediaShop;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kyh
 *
 * 2020.07.29
 */

@SuppressWarnings("serial")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocalboxCond implements Serializable{
	private Integer localboxCode;
	private Integer bannerCode;
	private Short bannerType;
	private List<Integer> bannerCodes;
	private Integer memberCode;
	private String title;
	private String localerName;
	private String companyType;
	private String areaCode;
	private Integer pageStartNo;
	private Integer pageInDataNum;
}
