package com.mrc.db.dto.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by jeon.sh@altsoft.kr on 2020-11-02
 * Discription : 회원 탈퇴
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberWithdraw implements Serializable {
    private Integer memberCode;
    private String memberPw;
    private String remark;
    private Boolean isReason1;
    private Boolean isReason2;
    private Boolean isReason3;
    private Boolean isReason4;
    private int insertCode;

    private Timestamp insertDate;
    @Builder.Default
    private Integer waitingDay = 0;
    private Integer withdrawCode;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp withdrawDate;
}
