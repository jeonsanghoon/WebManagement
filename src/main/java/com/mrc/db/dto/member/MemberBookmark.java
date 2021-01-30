package com.mrc.db.dto.member;

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
public class MemberBookmark implements Serializable {
	private Integer bookmarkCode;
	private String bookmarkName;
	private Integer bookmarkType;
	private Integer memberCode;
	private Integer bannerCode;
	private Integer localboxCode;
	private Integer signCode;
	private String remark;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp insertDate;
	private Boolean isBookmark;
}
