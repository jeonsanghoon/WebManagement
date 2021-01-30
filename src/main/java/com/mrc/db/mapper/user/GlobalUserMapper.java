package com.mrc.db.mapper.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 사용자 글로벌 맵퍼
 * @author jeon.sh
 *
 */
@Component
public class GlobalUserMapper {
	public static IUserBannerMapper userBanner;
	public static IUserLocalboxMapper userLocalboxMapper;
	public static IUserCommonMapper userCommonMapper;
	@Autowired
	private GlobalUserMapper(IUserBannerMapper _userbanner
			                 ,IUserLocalboxMapper _userLocalboxMapper
			                 ,IUserCommonMapper _userCommonMapper
			       ) {
		userBanner = _userbanner;
		userLocalboxMapper = _userLocalboxMapper;
		userCommonMapper = _userCommonMapper;
	}
}
