package com.mrc.db.dto.member;

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
public class MemberList implements Serializable {
	private int memberCode;
	private String memberId;
	private String memberName;
	private String authTypeName;
	private String memberImg;
	private Float memberImgsize;
	private String birthDate;
}
