package com.mrc.db.dto.common;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UploadFiles {
	/**
	 * 업로드파일명(업로드시 변경됨)
	 */
	private String fileName;
	/**
	 * 업로드할 파일의 원래 명칭
	 */
	private String oriFileName;
	/**
	 * 파일크기(기준 byte)
	 */
	private Long fileSize;
	/**
	 * 파일 content type
	 */
	private String fileContentType;

	/**
	 * 
	 */
	private String attachmentUrl;
	
	private String thumbNailImage;
	
	private String pickThumb;
	
	private List<String> arrThumbNailImage;
}
