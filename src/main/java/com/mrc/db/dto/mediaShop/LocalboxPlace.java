package com.mrc.db.dto.mediaShop;

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
 * 2020.07.29
 */

@SuppressWarnings("serial")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocalboxPlace implements Serializable {
	private Integer placeCode;
	private Integer localboxCode;
	private String areaCode;
	private String region;
	private String address;
	private String roadAddress;
	private String addressDetail;
	private String zipCode;
	private Double latitude;
	private Double longitude;
	private Integer radius;
	@Builder.Default
	private Boolean hide = false;
	
	private int insertCode;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp insertDate;
	private int updateCode;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp updateDate;
}
