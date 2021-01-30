/**
 * 
 */
package com.mrc.db.mapper;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jsh 글로벌 맵퍼
 */
@Component
public class GlobalMapper {

	@Getter
	private static MemberMapper memberMapper;
	@Getter
	private static MemberCompanyMapper memberCompanyMapper;
	@Getter
	private static MemberWithdrawMapper memberWithdrawMapper;
	@Getter
	private static CommonMapper commonMapper;
	@Getter
	private static MenuMapper menuMapper;
	@Getter
	private static FileMapper fileMapper;
	@Getter
	private static BannerMapper bannerMapper;
	@Getter
	private static AddressLocationMapper addressLocationMapper;
	@Getter
	private static AddressLocationBaseMapper addressLocationBaseMapper;
	@Getter
	private static MapSquareMapper mapSquareMapper;
	@Getter
	private static KeywordMapper keywordMapper;
	@Getter
	private static BoardMapper boardMapper;
	@Getter
	private static PersistentLoginsMapper persistentLoginsMapper;
	@Getter
	private static MediaShopMapper mediaShopMapper;
	@Getter
	private static AddressAreaMapper addressAreaMapper;
	@Getter
	private static MemberBookmarkMapper memberBookmarkMapper;
	@Getter
	private static AddressLocation1Mapper addressLocation1Mapper;
	@Getter
	private static SignageMapper signageMapper;
	@Getter
	private static SignageBannerMapper signageBannerMapper;
	@Getter
	private static SignagePlaceMapper signagePlaceMapper;



	@Autowired
	private GlobalMapper(MemberMapper member
			            , MemberCompanyMapper memberCompany
			            , MemberWithdrawMapper memberWithdraw
						, CommonMapper common
						, MenuMapper menu
			            , FileMapper file
			            , AddressLocationMapper addressLocation
			            , AddressLocationBaseMapper addressLocationBase
			            , MapSquareMapper mapSquare
			            , BannerMapper banner
			            , KeywordMapper keyword
			            , BoardMapper board
			            , PersistentLoginsMapper persistentLogins
			            , MediaShopMapper mediaShop
			            , AddressAreaMapper addressArea
			            , MemberBookmarkMapper memberBookmark
			            , AddressLocation1Mapper _addressLocation1
			            , SignageMapper signage
			            , SignageBannerMapper signageBanner
			            , SignagePlaceMapper signagePlace)
	{
		memberMapper = member;
		memberCompanyMapper = memberCompany;
		memberWithdrawMapper = memberWithdraw;
		commonMapper = common;
		menuMapper = menu;
		fileMapper = file;
		bannerMapper = banner;
		addressLocationMapper = addressLocation;
		addressLocationBaseMapper = addressLocationBase;
		mapSquareMapper = mapSquare;
		keywordMapper = keyword;
		boardMapper = board;
		persistentLoginsMapper = persistentLogins;
		mediaShopMapper = mediaShop;
		addressAreaMapper = addressArea;
		memberBookmarkMapper = memberBookmark;
		addressLocation1Mapper = _addressLocation1;
		signageMapper = signage;
		signageBannerMapper = signageBanner;
		signagePlaceMapper = signagePlace;
	}
}
