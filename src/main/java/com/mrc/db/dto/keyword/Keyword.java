package com.mrc.db.dto.keyword;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.poi.hpsf.Decimal;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @Author: kyh
 * @Date: 2020. 4. 2.
 */

@SuppressWarnings("serial")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Keyword implements Serializable {
	private Integer keywordCode;
	/**
	 * 키워드유형(T_COMMON:B002=> 1:지역, 2:일반)
	 */
	private Short keywordType;
	private String keywordName;
	private String keywordDesc;
	private Integer baseCode;
	@Builder.Default
	private Boolean isSynonym= false;
	private String chosung;
	private String jungsung;
	private String jongsung;
	private String keywordUnits;
	private Decimal latitude;
	private Decimal longitude;
	@Builder.Default
	private Short status = 1;
	@Builder.Default
	private Boolean hide = false;
	@Builder.Default
	private Integer searchCnt=0;
	private int insertCode;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp insertDate;
	private int updateCode;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp updateDate;
	
}
