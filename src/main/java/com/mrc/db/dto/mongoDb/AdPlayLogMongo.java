package com.mrc.db.dto.mongoDb;


import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Field;


/**
 * @author  : jsh
 * @Date    : 2020. 3. 24.
 * @Version : 
 */
@Document(collection="adPlayLog")
@Data
@Builder
@ToString
public class AdPlayLogMongo {
	private ObjectId _id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @Field("REG_DATE")
    private Date regDate;
    @Field("REG_DAY")
    private String regDay;
    /**
     * 디바이스종류(T_COMMON MAIN_CODE : L003) 1:모바일, 2:로컬박스
     */
    @Field("DEVICE_KIND")
    private Integer deviceKind;
    
    /// 디바이스종류
    @Field("DEVICE_KIND_NAME")
    private String deviceKindName;
    
    /// 화면분할(T_COMMON => MAIN_CODE: H002 1:1Frame, 12:12Frame)
    @Field("FRAME_TYPE")
    private Integer frameType;
   
    /// 화면분할(T_COMMON => MAIN_CODE: H002 1:1Frame, 12:12Frame)
    @Field("FRAME_TYPE_NAME")
    private String frameTypeName;

    /// 재생유형(T_COMON P004, 1:조회,2:클릭,3:재생)
    @Field("PLAY_TYPE")
    private Integer playType;
    /// <summary>
    /// 재생유형(T_COMON P004, 1:조회,2:클릭,3:재생)
    /// </summary>
    @Field("PLAY_TYPE_NAME")
    private String playTypeName;
    /// 로컬박스코드(T_DEVICE)
    @Field("DEVICE_CODE")
    private Long deviceCode;

    /// 로컬박스명
    @Field("DEVICE_NAME")
    private String deviceName;
    /**
     * 광고코드(T_AD) 
     */
    @Field("AD_CODE")
    private Long adCode;
    /**
     * 광고제목 
     */
    @Field("TITLE")
    private String title;

    /// <summary>       
    /// 플레이시간 NUMERIC(7,3) PLAY_TYPE 클릭(2) 일경우 60초로 등록
    /// </summary>	  
    @Field("PLAY_TIME")
    private Long playTime;
    /// <summary>       
    /// 비고
    /// </summary>
    @Field("REMARK")
    private String remark;
    /// <summary>
    ///  T_AD : BANNER_TYPE2 >> 배너유형(1:이미지,2:동영상,3:유튜브-T_COMMON : A010)
    /// </summary>
    @Field("BANNER_TYPE2")
    private Integer bannerType2;
    /// <summary>
    ///  T_AD : BANNER_TYPE2 >> 배너유형(1:이미지,2:동영상,3:유튜브-T_COMMON : A010)
    /// </summary>

    @Field("BANNER_TYPE2_NAME")
    private String bannerType2Name;

    /// 배너구분(B008 1:내배너 2:일반배너
    @Field("BANNER_KIND")
    private Integer bannerKind;
    @Field("BANNER_KIND_NAME")
    private String bannerKindName;;
}
