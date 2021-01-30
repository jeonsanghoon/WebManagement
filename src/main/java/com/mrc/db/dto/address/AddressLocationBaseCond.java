package com.mrc.db.dto.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by mrc0700@gmail.com on 2021-01-28
 * Discription :
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressLocationBaseCond {
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
     * utm-k x좌표
     */
    private Double x;
    /**
     * utm-k y좌표
     */
    private Double y;
    /**
     * 스케쥴러를 통한 마이그레이션 적용여부
     */
    private Boolean isNullApply;
    /**
     * Search_Latitude값이 null일 경우 체크
     */
    private Boolean isNullSearchLatitude;
}
