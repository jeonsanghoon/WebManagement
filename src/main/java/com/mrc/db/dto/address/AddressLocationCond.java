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
public class AddressLocationCond implements Serializable {
	private Integer idx;
	private Double searchLatitude;
	private Double searchLongitude;

	private Integer page;
	private Integer pageSize ;
	private Integer startRow;
	private Integer startCode;
	private Boolean isXnull;
}
