package com.mrc.db.dto.mongoDb;
import java.util.Date;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;


@Document(collection="log")
@Data
public class Log {
	@Id
	private String id;
	@Field("member_code")
	private Integer memberCode;
	@Field("member_name")
	private String memberName;
	@Field("deviceType")
	private String deviceType;/*pc, mobile, signage*/
	@Field("page_name")
	private String pageName;
	@Field("page_url")
	private String pageUrl;
	private String params;
	private Double latitude;
	private Double longitude;
	@Field("banner_code")
	private Integer bannerCode;
	private String title;
	@Field("sign_code")
	private Integer signCode;
	@Field("sign_name")
	private String signName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	@Field("reg_date")
    private Date regDate;
}
