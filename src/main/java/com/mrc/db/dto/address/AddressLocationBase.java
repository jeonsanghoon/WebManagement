package com.mrc.db.dto.address;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by mrc0700@gmail.com on 2021-01-13
 * Discription :
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressLocationBase implements Serializable {
    /**
     * 기본코드
     */
    private Integer locationCode;
    /**
     * 읍면동코드(법정동)
     */
    private String dongCode;
    /**
     * 시도명
     */
    private String sidoName;
    /**
     * 시군구명
     */
    private String sigunguName;
    /**
     * 읍명동명(법정동)
     */
    private String dongName;
    /**
     * 읍명동명(행정동)
     */
    private String dong2Name;
    /**
     * 우편번호
     */
    private String zipCode;
    /**
     * utm-k x좌표
     */
    private Double x;
    /**
     * utm-k y좌표
     */
    private Double y;
    /**
     * 검색용경도
     */
    private Double searchLongitude;
    /**
     * 검색용위도
     */
    private Double searchLatitude;

    /**
     * 기본경도(t_map_square의 longitude)
     */
    private Double baseLongitude;
    /**
     * 기본위도(t_map_square의 latitude)
     */
    private Double baseLatitude;
    /**
     * 경도
     */
    private Double longitude;
    /**
     * 위도
     */
    private Double latitude;
    /**
     * 데이터 추가 또는 업데이트 시간
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp updateDate;

    /**
     * 스케쥴러를 통한 마이그레이션 적용일자
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp applyDate;

    @Builder.Default
    private Boolean hide = false;

    /**
     * 이동유 31:생성, 34:변동. 63:삭제
     */
    private Integer saveMode;


}
