package com.mrc.db.dto.banner;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author kyh
 *
 * 2020.06.12
 */

@SuppressWarnings("serial")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BannerCond implements Serializable {
	private Integer bannerCode;
	private Integer memberCode;
	private String memberId;
	private String title;
	private List<Integer> deviceType2;
	private List<Short> bannerType;
	private List<Short> service;
	private List<Short> statuses;

	private Short status ;
	private String dateSeq;
	@Builder.Default
	private Integer pageStartNo = 0;
	@Builder.Default
	private Integer pageInDataNum = 100;
	private Integer frameType;
	private Integer signCode;
	private Integer notSignCode;
	private Integer loginCode;
	private String inStatus;
	@Builder.Default
	private String orderBy ="a.update_date desc";

}
