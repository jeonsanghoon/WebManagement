package com.mrc.db.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

 
@SuppressWarnings("serial")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberCond implements Serializable {
	private Integer memberCode;
	private String memberId;
	private String memberPw;
	private String memberPwReg;
	private String memberName;
	private Integer authType;
	private String token;
	private String birthDate;
	private Boolean hide;

	private String businessNumber;
	private Boolean rememberMe;
	private Integer insertCode;
}
