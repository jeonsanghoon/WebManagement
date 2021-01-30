package com.mrc.db.dto.board;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;


import com.mrc.db.dto.common.UploadFiles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Board implements Serializable {
	/**
	 * 순번
	 */
	private Integer idx;
	private Integer boardCode;
	@Builder.Default
	private Integer boardType=1;
	private String title;
	private String content;
	@Builder.Default
	private Integer searchCnt=0;
	@Builder.Default
	private Boolean isREAD = false;
	@Builder.Default
	private Boolean hide=false;
	private Integer insertCode;
	private String insertName;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime insertDate;

	private List<UploadFiles> uploadFiles;
	/**
	 * 검색 총 갯수
	 */
	private Integer totRows;
}
