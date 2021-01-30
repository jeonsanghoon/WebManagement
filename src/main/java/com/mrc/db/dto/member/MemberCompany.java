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
 * @Author: kyh
 * @Date: 2020. 4. 27.
 */

@SuppressWarnings("serial")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberCompany implements Serializable {
	private Integer memberCode;
	private String branchName;
	private String companyName;
	private Integer businessType;
	private String businessNumber;
	private Short profitType;
	/**
	 * 중복 체크시 사용되는 코드
	 */
	private Integer chkMemberCode;
	private int updateCode;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp updateDate;
	private String memberPw;
}
