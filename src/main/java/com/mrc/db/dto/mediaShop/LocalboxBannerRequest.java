package com.mrc.db.dto.mediaShop;

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
public class LocalboxBannerRequest implements Serializable {
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
	private Integer approvalMemberCode;
	private Integer frDate;
	private Integer toDate;
	@Builder.Default
	private Short status = 1;
	private Short rejectType;
	private String rejectDesc;
	private String remark;
	private List<Integer> localers;
	
	private int insertCode;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp insertDate;
	private int updateCode;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp updateDate;
}
