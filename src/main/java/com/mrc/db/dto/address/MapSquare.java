package com.mrc.db.dto.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MapSquare {
	private Integer squareCode;
	private Double searchLatitude;
	private Double searchLongitude;
	private Double latitude;
	private Double longitude;
}
