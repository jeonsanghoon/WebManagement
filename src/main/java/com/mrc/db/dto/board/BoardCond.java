package com.mrc.db.dto.board;

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
public class BoardCond implements Serializable {
	private Integer boardCode;
	@Builder.Default
	private Integer boardType =1;
	private String title;
	private String content;
	private Integer insertCode;
	private String insertName;
	/**
	 * 로그인한 사용자 게시판 읽음 처리시 사용
	 */
	private Integer memberCode;
	private Boolean isRead;

	private String sortBy;
	@Builder.Default
	private Integer startRow = 0;
	@Builder.Default
	private Integer rowSize = 5;
	/**
	 * 읽은 글 코드
	 */
	private String boardCodes;
}
