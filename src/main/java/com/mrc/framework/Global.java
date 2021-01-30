/**
 * 
 */
package com.mrc.framework;

import groovy.lang.Singleton;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;




/**
 * @author jsh
 *
 */
@Component
public class Global {
	@Getter
	private static CommonInfo commonInfo;
	@Getter
	private static SecurityInfo securityInfo;
	public static DataInfo getDataInfo() {
		return dataInfo;
	}
	private static DataInfo dataInfo;
	@Getter
	private static FileInfo fileInfo;
	/**
	 * Thread 없는 메일 보내기
	 */
	@Getter
	private static MailInfoBase mailInfoBase;
	/**
	 * Thread 있는 메일 보내기
	 */
	@Getter
	private static MailInfo mailInfo;
	@Getter
	private static KakaoApi kakaoApi;
	@Getter
	private static ConfigInfo configInfo;
	@Getter
	private static MapInfo mapInfo;
	@Getter
	private static NumberInfo numberInfo;
	@Getter
	private static ExcelParser excelParser;
	@Autowired
	private MailInfoBase _mailInfoBase;
	@SuppressWarnings("rawtypes")
	@Autowired
	private MailInfo _maininfo;
	
	@PostConstruct
	private void initialize() {
		commonInfo = new CommonInfo();
		securityInfo = new SecurityInfo();
		dataInfo = new DataInfo();
		fileInfo = new FileInfo();

		kakaoApi = new KakaoApi();
		mailInfoBase = this._mailInfoBase;
		mailInfo = this._maininfo;
		//ExcelInfo = new ExcelInfo<T>();
		configInfo = new ConfigInfo();
		mapInfo = new MapInfo();
		numberInfo = new NumberInfo();
		excelParser = new ExcelParser();
	}
}
