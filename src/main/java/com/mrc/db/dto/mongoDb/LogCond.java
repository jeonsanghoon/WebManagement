package com.mrc.db.dto.mongoDb;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogCond implements Serializable {
	
	/**
	 * 
	 */
	private String searchText;
	private static final long serialVersionUID = 1L;
	@Default
	private Integer page=1;
	@Default
	private Integer pageSize=10;
	private String _id;
	private Integer memberCode;
	private String memberName;
	private String frDate;
	private String toDate;
	
	private String deviceType;/*pc, mobile, signage*/
	@Default
	private String sort="reg_date";
}
