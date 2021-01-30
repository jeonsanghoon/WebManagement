package com.mrc.db.dto.member;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 나의 공간 > 개인정보
 */
@Data
@Builder
public class MemberInfo implements Serializable {
    private Integer memberCode;
    private String address;
    private String addressDetail;
    private String roadAddress;
    private String zipCode;
    private String businessNumber;
    private String businessType;
    private String phone;
    private Integer regCode;
}
