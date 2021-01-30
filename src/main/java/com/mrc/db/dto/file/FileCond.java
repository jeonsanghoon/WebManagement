package com.mrc.db.dto.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileCond implements Serializable {
	private Integer fileCode;
	private String tableName;
	private String tableKey;
	private Integer fileSeq;
}
