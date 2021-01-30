package com.mrc.db.dto.signage;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@SuppressWarnings("serial")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignagePlace implements Serializable {
	
	private Integer placeCode;
	/**
	 * 광고영역구분 (T_COMMON: P003 1:실제주소 2:광고영역)
	 */
	@Builder.Default
	private Integer placeType=1;
	
	private Integer signCode;
	private String areaCode;
	private String region;
	private String address;
	private String address2;
	private String roadAddress;
	private String zipCode;
	private String building;
	private Double latitude;
	private Double longitude;
	private Integer radius;
	private Integer limitRadius;
	@Builder.Default
	private boolean hide = false;
	
	private Integer insertCode;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp insertCate;
	private Integer updateCode;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp updateDate;
}
