package com.mrc.db.dto.file;

import java.io.Serializable;
import java.sql.Timestamp;

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
public class File implements Serializable {
	private Integer idx;
	private Integer fileCode;
	private String tableName;
	private String tableKey;
	private Integer fileSeq;
	private String fileName;
	private String fileExt;
	private Integer fileSize;
	private String fileUrl;
	private String remark;
	private Integer insertCode;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp insertDate;

}
