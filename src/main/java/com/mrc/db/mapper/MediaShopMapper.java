package com.mrc.db.mapper;

import com.mrc.db.dto.banner.Banner;
import com.mrc.db.dto.mediaShop.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author kyh
 *
 * 2020.07.29
 */

@Mapper
public interface MediaShopMapper {
	Localbox getLocalBox(LocalboxCond cond);
	List<String> getLocalBoxKeyword(LocalboxCond cond);
	LocalboxPlace getLocalBoxPlace(LocalboxCond cond);
	void save(Localbox data);
	void deleteLocalboxKeyword(Integer localbox_code);
	void saveLocalboxKeyword(LocalboxKeyword data);
	void deleteLocalboxPlace(LocalboxPlace data);
	void saveLocalboxPlace(LocalboxPlace data);
	LocalboxSimple getLocalBoxSimple(LocalboxCond cond);
	List<LocalboxBanner> getLocalBoxBannerList(LocalboxCond cond);
	List<LocalboxBanner> getLocalBoxPreviewBannerList(LocalboxCond cond);
	List<LocalboxPlace> getLocalBoxBannerPlaceList(LocalboxCond cond);
	Integer getTotalNum();
	Integer getLocalBoxBannerCount(Integer member_code);
	Integer getLocalBoxCode(Integer memeber_code);
	void deleteLocalBoxBanner(Integer banner_code);
	void saveLocalboxBanner(Banner data);
	void updateLocalBoxBanner(LocalboxBanner banners);
	Integer getLocalBoxCode(LocalboxCond cond);
	List<Localer> getLocalerList(LocalboxCond cond);
	void deleteBannerShare(LocalboxBannerRequest data);
	void saveBannerShare(LocalboxBannerRequest data);
	List<LocalboxBannerRequest> getBannerShare(LocalboxCond cond);
}
