package com.mrc.db.dto.menu;

import lombok.Builder;
import lombok.Data;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuCond implements Serializable {
	private Integer menuCode;
	private Integer projectType;
	private String searchCode;
	private Integer menuAuth;
	private String menuName;
	private Boolean hide;
}
