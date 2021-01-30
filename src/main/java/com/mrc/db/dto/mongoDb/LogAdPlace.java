package com.mrc.db.dto.mongoDb;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="log_adPlace")
@Data
public class LogAdPlace implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	/**
	 * mongodb 순번
	 */
	private String id;
	/**
	 * 구분 순번 : System.currentTimeMillis()  1970년 1월 1일 오전 0시와의 차이의 long 값
	 */
	private Long idx;
	/**
	 * 구분 순번별 서브 순번
	 */
	@Field("idx_sub")
	private Integer idx_sub;
	@Field("member_code")
	private Integer memberCode;
	@Field("member_name")
	private String memberName;
	private Double latitude;
	private Double longitude;
	private String address;
	@Field("road_address")
	private String roadAddress;
	private Integer radius;
	@Field("limit_radius")
	private Integer limitRadius;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	@Field("reg_date")
	private Date regDate;
}
