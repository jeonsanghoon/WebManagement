package com.mrc.db.dao.interfaces;

import com.mrc.db.dto.common.ResultData;
import com.mrc.db.dto.keyword.Keyword;
import com.mrc.db.dto.mediaShop.LocalboxBanner;
import com.mrc.db.dto.mediaShop.LocalboxBannerRequest;
import com.mrc.db.dto.mediaShop.LocalboxPlace;
import com.mrc.db.dto.mediaShop.LocalboxSimple;

import java.util.List;
import java.util.Map;

/**
 * @author kyh
 *
 * 2020.07.29
 */

@SuppressWarnings("hiding")
public interface MediaShopDao<K,V>  extends BaseDao<K,V> {
	 Map<String, Object> getLocalBox(K cond);
	
	 ResultData save(V localbox, List<Keyword> keywords, LocalboxPlace place);

	 LocalboxSimple getLocalBoxSimple(K cond);
	
	 Map<String, Object> getLocalBoxBannerList(K cond);
	
	 Map<String, Object> getLocalBoxPreviewBannerList(K cond);

	 ResultData updateLocalBoxBanner(List<LocalboxBanner> banners);
	
	 Integer getLocalBoxCode(K cond);

	 Map<String, Object> getLocalerList(K cond);
	
	 ResultData saveBannerShare(List<LocalboxBannerRequest> data);

	 List<LocalboxBannerRequest> getBannerShare(K cond);
}
