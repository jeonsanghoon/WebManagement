 package com.mrc.db.dto.common;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Common implements Serializable {
	@Builder.Default
	private Integer commonCode= -1;
	private String mainCode;
	private Integer subCode;
	private String name;
	private int orderSeq;
	private String refData1;
	private String refData2;
	private String refData3;
	private String refData4;
	private Boolean hide;
	private int insertCode;
	private String insertName;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime insertDate;
	private int updateCode;
	private String updateName;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updateDate;
}
