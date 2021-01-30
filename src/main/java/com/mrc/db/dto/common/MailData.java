/**
 * 
 */
package com.mrc.db.dto.common;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;

/**
 * @author jsh
 * 메일 관련 셋팅정보
 */
@Data
@Builder
public class MailData {
	/*
	 * Html 템플릿 Default : "/templeate/mail-template.html"
	 */
	private String templeateName;
	/*
	 * 이메일 제목
	 */
	private String title;
	/*
	 * 보내는 사람 메일주소 Default 
	 */
	@Default
	private String fromMail = "webservice@altsoft.kr";
	/*
	 * 보내는 사람 표기명
	 */
	@Default
	private String fromMailName = "온닫이 고객센터";
	/*
	 * 받는사람 메일주소
	 */
	private String toMail;
	/*
	 * 받는사람 표기명 
	 */
	@Default
	private String toMailName="";
	/*
	 * 참조자 주소
	 */
	private String ccMail;
	/*
	 * 참조자 표기명
	 */
	@Default
	private String ccMailName="";
	private String message; 
}
