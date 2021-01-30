package com.mrc.db.mapper;

import com.mrc.db.dto.address.AddressArea;
import com.mrc.db.dto.banner.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author kyh
 *
 * 2020.06.15
 */


@Mapper
public interface BannerMapper {
	Banner getData(BannerCond cond);
	
	List<Banner> getList(BannerCond cond);
	
	List<Banner> getBannerStoreList(BannerCond cond);
	
	Integer getTotalBannerNum();
	
	void save(Banner data);
	
	Integer getDeleteTempBannerCode(Banner data);
	
	void delete(Integer banner_code);
	
	void deleteBannerPlaceSquare(Integer place_code);
	
	void deleteBannerPlace(Integer banner_code);
	
	void saveBannerPlace(BannerPlace data);
	
	AddressArea getAddressArea(String code);
	
	void saveBannerPlaceSquare(BannerPlaceSquare data);
	
	void deleteBannerKeyword(Integer banner_code);
	
	void saveBannerKeyword(BannerKeyword data);
	
	void deleteBannerSub(Integer banner_code);
	
	void saveBannerSub(BannerSub data);
	
	List<BannerPlaceTag> getPlaceTag(BannerPlaceTagCond cond);
	
	Integer getRadiusType(Integer type);
	
	Integer getTempBannerCnt(Banner data);
	
	List<Banner> getSimpleTempBanner(BannerCond cond);
	
	List<String> getBannerKeyword(Integer banner_code);
	
	List<BannerPlace> getBannerPlace(Integer banner_code);
	
	List<BannerPlaceSquare> getBannerPlaceSquare(Integer banner_code);
	
	List<BannerSub> getBannerSub(Integer banner_code);
	
	void saveBannerStatus(Banner data);

	List<BannerRequest> getReqBannerShareList(BannerCond cond);

	List<BannerRequest> getResBannerShareList(BannerCond cond);
}
