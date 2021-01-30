package com.mrc.db.dto.mediaShop;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kyh
 *
 * 2020.09.15
 */

@SuppressWarnings("serial")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Localer implements Serializable{
	private Integer rowNum;
	private Integer localboxCode;
	private Integer memberCode;
	private String memberId;
	private String profileUrl;
	private String nickname;
	private String companyName;
	private String companyType;
	private String address;
}
