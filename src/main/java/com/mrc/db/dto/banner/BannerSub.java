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
public class BannerSub implements Serializable {
	private Integer subIdx;
	private Integer bannerCode;
	private Short seq;
	private Integer playType;
	private String thumbImageUrl;
	private String bannerImageUrl;
	private String bannerMovieUrl;
	private String content;
	private Integer searchCnt;
	private Integer bookmarkCnt;
	private int insertCode;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp insertDate;
}
