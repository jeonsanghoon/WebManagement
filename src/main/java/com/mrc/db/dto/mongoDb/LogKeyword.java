package com.mrc.db.dto.mongoDb;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="log_keyword")
@Data
@Builder
public class LogKeyword {
	@Field("keywordCode")
	Integer keywordCode;
	@Field("keyword_name")
	String keywordName;
	private Double latitude;
	private Double longitude;
	@Field("member_code")
	private Integer memberCode;
	@Field("member_name")
	private String memberName;
	/*t_map_square 테이블의 square_code */
	@Field("square_code")
	private Integer squareCode;
	@Field("area_code")
	private String areaCode;
	@Field("area_name")
	private String areaName;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	@Field("reg_date")
	@Builder.Default
	private Date regDate = new Date();
	
}
