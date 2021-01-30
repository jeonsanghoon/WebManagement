package com.mrc.db.dao.interfaces;

import com.mrc.db.dto.banner.*;
import com.mrc.db.dto.common.ResultData;
import com.mrc.db.dto.keyword.Keyword;

import java.util.List;
import java.util.Map;

/**
 * @Author: kyh
 * @Date: 2020. 6. 10.
 */

@SuppressWarnings("hiding")
public
interface BannerDao<K,V>  extends BaseDao<K,V> {
	static final Short TEMP_BANNERE_CNT = 10;
	static final Short BANNER_SERVICE_OFF = 9;
	static final Short BANNER_SERVICE_ON = 99;

	List<Banner> getBannerStoreList(K cond);

	ResultData save(V banner, List<Keyword> keywords, List<BannerPlace> places,
						   List<BannerSub> bannerSub, List<List<BannerPlaceSquare>> placeSquare);

	List<List<BannerPlaceTag>> getPlaceTag(List<BannerPlaceTagCond> cond);

	Integer getRadiusType(Integer type);

	List<V> getSimpleTempBanner(K cond);

	Map<String, Object> getBanner(Integer bannerCode);

	ResultData saveBannerStatus(Banner data) throws Exception;

	ResultData delete(K cond) throws Exception;

	BannerShareList getReqBannerShareList(K cond);

	BannerShareList getResBannerShareList(K cond);
}