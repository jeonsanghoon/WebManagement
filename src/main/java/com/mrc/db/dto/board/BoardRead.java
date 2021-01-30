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
public class BoardRead implements Serializable {
	public Integer boardType;
	private int insertCode;
	private String boardCodes;
	private String boardCode;
}
