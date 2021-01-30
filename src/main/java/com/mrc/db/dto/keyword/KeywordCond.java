package com.mrc.db.dto.keyword;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
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
public class KeywordCond implements Serializable {
	
	private String keywordName;
	private Integer keywordType;
	private Integer keywordCode;
	private Integer baseCode;
	private Boolean isSynonym;
	private String chosung;
	private String jungsung;
	private String jongsung;
	private String keywordUnits;
	@Default
	private String sortColumn1 = "search_cnt desc" ;
	@Default
	private String sortColumn2 = "keyword_name" ;
	private String sortType;
	@Default
	private Integer limit = 1000;
}
