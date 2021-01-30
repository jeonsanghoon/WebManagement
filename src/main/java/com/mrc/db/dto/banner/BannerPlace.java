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
 * @Date: 2020. 5. 17.
 */

@SuppressWarnings("serial")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BannerPlace implements Serializable {
	private Integer placeCode;
	private Integer bannerCode;
	private String areaCode;
	private String region;
	private String address;
	private String roadAddress;
	private String zipCode;
	private Double latitude;
	private Double longitude;
	private Integer radius;
	private Integer limitRadius;
	@Builder.Default
	private Boolean hide = false;
	
	private int insertCode;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp insertDate;
	private int updateCode;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp updateDate;
}
