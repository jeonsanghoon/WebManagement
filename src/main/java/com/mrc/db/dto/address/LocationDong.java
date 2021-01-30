package com.mrc.db.dto.address;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationDong implements Serializable {
	private String code;
	/**
	 * tMAPSQUARE 테이블 코드
	 * 
	 */
	private Integer squareCode;
	/**
	 * 경도
	 */
	private Double longitude;
	/**
	 * 위도
	 */
	private Double latitude;
	/**
	 * 시도명(표시)
	 */
	private String disSidoName;
	/**
	 * 시도명
	 */
	private String sidoName;
	/**
	 * 시군구명
	 */
	private String sigunguName;
	/**
	 * 행정동명
	 */
	private String dong2Name;
	/**
	 * tMAPSQUARE 해당구역의 행정동 비율
	 */
	private String gage;
}
