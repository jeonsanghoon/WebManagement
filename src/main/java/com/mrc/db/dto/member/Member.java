/**
 * 
 */
package com.mrc.db.dto.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author jsh
 *
 */

@SuppressWarnings("serial")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member implements Serializable {
	private Integer memberCode;
	private String memberId;
	private String memberName;
	private String nickname;
	@JsonIgnore
	private String memberPw;
	private String memberPwReg;
	@Builder.Default
	private Integer authType = 30;
	private String authTypeName;
	private String birthDate;
	private String phone;
	private Boolean service; 
	private String token;
	@Builder.Default
	private Boolean hide = false;
	
	private String branchName;
	private String companyName;
	private Integer businessType;
	private String businessTypeName;
	private String businessNumber;
	private Short profitType;
	private String areaName;
	
	private Integer localboxCode;
	private String zipCode;
	private String address;
	private String addressDetail;
	private String profileUrl;
	
	private int insertCode;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp insertDate;
	private int updateCode;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp updateDate;
	private int companyUpdateCode;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp companyUpdateDate;

}
