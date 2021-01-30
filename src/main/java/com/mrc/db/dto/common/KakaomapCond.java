package com.mrc.db.dto.common;

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
public class KakaomapCond implements Serializable {
	private Double lat;
	private Double longi;
	private String query;
	private Integer page;
	private Integer size;
}
