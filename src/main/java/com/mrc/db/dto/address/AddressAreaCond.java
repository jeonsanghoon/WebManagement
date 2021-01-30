package com.mrc.db.dto.address;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by mrc0700@gmail.com on 2021-01-19
 * Discription :
 */
@Getter
@Setter
@Builder
public class AddressAreaCond {
    private Integer level;
    private String code;
    private String name;
    private Integer keywordCode;
    private Boolean hide;
    private Boolean isNullLatitude;
}
