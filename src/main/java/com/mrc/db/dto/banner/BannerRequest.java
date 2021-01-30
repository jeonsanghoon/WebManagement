package com.mrc.db.dto.banner;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author kyh
 *
 * 2020.09.23
 */

@SuppressWarnings("serial")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BannerRequest implements Serializable {
    private Integer rowNum;
    private Integer requestCode;
    private Integer bannerCode;
    @Builder.Default
    private String requestDesc = "";
    @Builder.Default
    private Boolean isMobile = false;
    @Builder.Default
    private Boolean isShare = false;
    @Builder.Default
    private Boolean isLocal = false;
    private Integer requestMemberCode;
    private String approvalMemberCode;
    private String frDate;
    private Timestamp reqDate;
    @Builder.Default
    private Short reqStatus = 1;
    private Short rejectType;
    private String rejectDesc;
    private String remark;
    private List<String> localers;
    private Short bannerType;
    private Short deviceType;
    private Integer deviceType2;
    private Short frameType;
    @Builder.Default
    private Boolean isVertical = false;
    @Builder.Default
    private Boolean isPrivate = false;
    private Short playType;
    private Integer memberCode;
    private String thumbImageUrl;
    private String bannerImageUrl;
    private String bannerMovieUrl;
    private String title;
    private String subTitle;
    private String content;

    private int insertCode;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp insertDate;
    private int updateCode;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp updateDate;
}
