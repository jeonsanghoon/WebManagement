package com.mrc.db.dto.signage;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by mrc0700@gmail.com on 2020-12-14
 * Discription :
 */
@Builder
@Data
public class SignageStatus implements Serializable {
    private Integer signCode;
    private Boolean hide;
    private Integer updateCode;
}
