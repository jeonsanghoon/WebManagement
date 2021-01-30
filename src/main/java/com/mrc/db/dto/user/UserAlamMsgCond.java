package com.mrc.db.dto.user;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAlamMsgCond implements Serializable{
	@Default
	private Integer page = 0;
	@Default
	private Integer pageSize=20;
	private Integer memberCode;
}
