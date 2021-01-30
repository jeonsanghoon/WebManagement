package com.mrc.db.dto.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * 주소 위치정보 
 * @author jsh
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressLocation implements Serializable {

	/**
	 * 기본키(순번)
	 */
	private Integer idx;
	/**
	 * 검색용경도
	 */
	private Double searchLongitude;
	/**
	 * 검색용위도
	 */
	private Double searchLatitude;
	/**
	 * 경도
	 */
	private Double longitude;
	/**
	 * 위도
	 */
	private Double latitude;
	/**
	 * 법정동코드
	 */
	private String dongCode;
	/**
	 * 시도명
	 */
	private String sidoName;
	/**
	 * 시군구명
	 */
	private String sigunguName;
	/**
	 * 읍명동명(법정동)
	 */
	private String dongName;
	/**
	 * 읍명동명(행정동)
	 */
	private String dong2Name;
	/**
	 * 법정동비율
	 */
	private Double gageRate;


	
}
