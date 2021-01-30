package com.mrc.db.dto.menu;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Menu implements Serializable{
	private Integer menuCode;
	private Integer projectType;
	private String searchCode;
	private String menuName;
	private String fullMenuName;
	private Integer menuAuth;
	private String menuAuthName;
	private Integer menuLevel;
	private String menuUrl;
	private String menuIcon;
	private String menuIconStyle;
	private String remark;
	private Boolean hide;
	private Integer insertCode;
	private String insertName;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp insertDate;
	private Integer updateCode;
	private String updateName;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp updateDate;
}
