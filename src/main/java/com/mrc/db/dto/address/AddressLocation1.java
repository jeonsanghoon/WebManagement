package com.mrc.db.dto.address;

import java.io.Serializable;
import java.math.BigInteger;
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
public class AddressLocation1 implements Serializable{
	private Integer idx;
	private String code;
	private Double searchLatitude;
	private Double searchLongitude;
	private String dongCode;
	private Double latitude;
	private Double longitude;
	private String sidoName;
	private String sigunguName;
	private String dong2Name;
	private Double gageRate;
	private BigInteger searchCnt;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp insertDate;
}
