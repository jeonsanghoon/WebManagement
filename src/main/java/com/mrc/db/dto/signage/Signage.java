package com.mrc.db.dto.signage;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@SuppressWarnings("serial")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Signage implements Serializable {
	private Integer signCode;
	private String signName;
	/**
	 * 사이니지유형(t_common : S002 1:내부광고용배너 2:외부광고용배너)
	 */
	private Integer signType;
	private String signTypeName;
	/**
	 * 화면분할(D002 1:1Frame 6:6Frame)
	 */
	private Integer frameType;
	private String frameTypeName;
	/**
	 * 세로설치여부(s004 : 1:세로 0:가로)
	 */
	private Boolean isVertical;
	private String isVerticalName;
	private Boolean isInside;
	private String isInsideName;
	private Integer memberCode;
	private String memberName;
	private String remark;
	@Builder.Default
	private boolean hide = false;
	private String hideName;

	private Integer BannerCnt;

	private Integer insertCode;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp insertDate;
	private Integer updateCode;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp updateDate;
	private String insertName;
	private String updateName;
	
	private List<SignagePlace> placeList;
}

