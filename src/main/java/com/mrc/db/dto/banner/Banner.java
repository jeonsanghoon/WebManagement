package com.mrc.db.dto.banner;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class Banner implements Serializable {
	private Integer rowNum;
	private Integer bannerCode;
	private Integer localboxCode;
	private Short bannerType;
	private Short deviceType;
	private Integer deviceType2;
	private Short frameType;
	@Builder.Default
	private Boolean isVertical = false;
	@Builder.Default
	private Boolean isPrivate = false;
	private Short playType;
	private Integer memberCode;
	private String thumbImageUrl;
	private String bannerImageUrl;
	private String bannerMovieUrl;
	private String title;
	private String subTitle;
	private String content;
	private Short status;
	private String remark;
	private Short bottomBannerType;
	private Short seq;
	@Builder.Default
	private Boolean hide = false;
	
	private int insertCode;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp insertDate;
	private int updateCode;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp updateDate;
	@Builder.Default
	private Integer totalRows = 0;
	@Builder.Default
	private Integer loginCode = 1;
	private Integer signCode;
}
