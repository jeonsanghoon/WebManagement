package com.mrc.db.dto.signage;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@SuppressWarnings("serial")
@Getter
@Setter
@Builder
public class SignageCond implements Serializable {
	private Integer signCode;
	private Integer placeCode;
	private Integer memberCode;
	private String signName;
	private String memberName;
	private Integer signType;
	private Integer frameType;
	private Boolean hide;
}
