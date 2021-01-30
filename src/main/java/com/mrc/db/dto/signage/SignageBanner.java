package com.mrc.db.dto.signage;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by mrc0700@gmail.com on 2020-12-04
 * Discription :
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignageBanner implements Serializable {
    private Integer code;
    private Integer signCode;
    private Integer bannerCode;
    @Builder.Default
    private String frDate="2000-01-01";
    @Builder.Default
    private String toDate="2999-12-31";
    private Short page;
    private Short seq;
    @Builder.Default
    private Boolean isPrivate=false;
    private Long requestCode;
    private String remark;
    private Integer insertCode;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp insertDate;
    private Integer updateCode;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp updateDate;
    private Integer loginCode;
}
