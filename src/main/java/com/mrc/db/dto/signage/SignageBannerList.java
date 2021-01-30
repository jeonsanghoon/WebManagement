package com.mrc.db.dto.signage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by mrc0700@gmail.com on 2020-12-23
 * Discription :
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignageBannerList implements Serializable {
    private Integer code;
    private Integer signCode;
    private Integer bannerCode;
    private String frDate;
    private String toDate;
    private Integer page;
    private Integer seq;
    private Boolean isPrivate;
    private String title;
    private String subTitle;
    private String thumbImageUrl;
    private Integer bannerType;
    private Integer loginCode;

}
