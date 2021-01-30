package com.mrc.framework;

import lombok.Data;
import lombok.Getter;


public class ConfigInfo {

	//private final String kakaoRestApiKey = "KakaoAK f14891693c4c1e9ed1ab2195e941c3dd";
	@Getter
	private final String kakaoRestApiKey = "KakaoAK 59b714dbe1dc970a47f5a316360edd58";
	@Getter
	private final String fileBasePath =  "C:/source/files/";
	@Getter
	private final Integer thumbImageSize = 150;
}
