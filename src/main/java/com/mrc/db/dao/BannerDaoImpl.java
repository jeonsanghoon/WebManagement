package com.mrc.db.dao;

import com.mrc.db.dao.interfaces.BannerDao;
import com.mrc.db.dao.interfaces.GlobalDao;
import com.mrc.db.dto.address.AddressArea;
import com.mrc.db.dto.banner.*;
import com.mrc.db.dto.common.ResultData;
import com.mrc.db.dto.common.ResultType;
import com.mrc.db.dto.keyword.Keyword;
import com.mrc.db.mapper.GlobalMapper;
import com.mrc.framework.Global;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

/**
 * @Author: kyh
 * @Date: 2020. 6. 10.
 */

@Repository
public class BannerDaoImpl extends BaseDaoImpl implements BannerDao<BannerCond, Banner> {
	@Override
	public List<Banner> getList(BannerCond cond) {
		return null;
	}

	@Override
	public Banner getData(BannerCond cond) {
		Banner banner = GlobalMapper.getBannerMapper().getData(cond);

		return banner;
	}

	@Transactional
	@Override
	public ResultData save(Banner data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public ResultData saveList(List<Banner> list) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public List<Banner> getBannerStoreList(BannerCond cond) {
		List<Banner> bannerList = GlobalMapper.getBannerMapper().getBannerStoreList(cond);
		Integer totalBannerNum = GlobalMapper.getBannerMapper().getTotalBannerNum();
		if(!Global.getDataInfo().isNullEmpty(cond.getDateSeq()))
		{
			if(cond.getDateSeq().toLowerCase(Locale.ROOT).equals("asc")){
				cond.setOrderBy("a.insert_date");
			}else{
				cond.setOrderBy("a.insert_date desc");
			}
		}
		if( bannerList.size()>0)
			bannerList.get(0).setTotalRows(totalBannerNum);
		return bannerList;
	}

	@Transactional
	@Override
	public ResultData save(Banner banner, List<Keyword> keywords, List<BannerPlace> places,
						   List<BannerSub> bannerSub, List<List<BannerPlaceSquare>> placeSquare) {

		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		try {
			Integer bannerCode = banner.getBannerCode();
			Integer insertCode = banner.getInsertCode();

			GlobalMapper.getBannerMapper().save(banner);

			if (bannerCode == null) {
				bannerCode = banner.getBannerCode();
			}

			rtn.setData(bannerCode.toString());

			if (places != null) {
				this.saveBannerPlace(bannerCode, insertCode, places, placeSquare);
			}

			if (keywords != null) {
				saveBannerKeyword(bannerCode, insertCode, saveKeyword(keywords));
			}

			if (bannerSub.size() != 0) {
				saveBannerSub(bannerCode, insertCode, bannerSub);
			}

			if (banner.getStatus() == 1) {
				if (GlobalMapper.getBannerMapper().getTempBannerCnt(banner) > TEMP_BANNERE_CNT) {
					Integer tempBannerCode = GlobalMapper.getBannerMapper().getDeleteTempBannerCode(banner);

					GlobalMapper.getBannerMapper().delete(tempBannerCode);
				}
			}
		} catch (Exception ex) {
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return rtn;
	}

	private List<Integer> saveKeyword(List<Keyword> keywords) {
		List<Integer> keywordCodes = new ArrayList<>();

		for (Keyword keyword : keywords) {
			GlobalMapper.getKeywordMapper().save(keyword);
			keywordCodes.add(keyword.getKeywordCode());
		}

		return keywordCodes;
	}


	private void saveBannerKeyword(Integer bannerCode, Integer insertCode, List<Integer> keywrodCodes) {

		BannerKeyword bannerKeyword = new BannerKeyword();

		bannerKeyword.setBannerCode(bannerCode);
		bannerKeyword.setInsertCode(insertCode);

		GlobalMapper.getBannerMapper().deleteBannerKeyword(bannerCode);

		for (Integer keywordCode : keywrodCodes) {
			bannerKeyword.setKeywordCode(keywordCode);

			GlobalMapper.getBannerMapper().saveBannerKeyword(bannerKeyword);
		}
	}


	private void saveBannerPlace(Integer bannerCode, Integer insertCode, List<BannerPlace> places,
			List<List<BannerPlaceSquare>> placeSquare) {

		GlobalMapper.getBannerMapper().deleteBannerPlace(bannerCode);

		Integer index = 0;
		for (BannerPlace place : places) {
			place.setBannerCode(bannerCode);
			place.setInsertCode(insertCode);
			GlobalMapper.getBannerMapper().saveBannerPlace(place);

			List<BannerPlaceSquare> tempPlaceSquare = placeSquare.get(index++);

			for (BannerPlaceSquare square : tempPlaceSquare) {
				AddressArea area = GlobalMapper.getBannerMapper().getAddressArea(square.getAreaCode());

				square.setBannerCode(bannerCode);
				square.setSquareType(area.getLevel());
				square.setInsertCode(insertCode);
				square.setPlaceCode(place.getPlaceCode());

				GlobalMapper.getBannerMapper().saveBannerPlaceSquare(square);
			}
		}
	}

	private void saveBannerSub(Integer bannerCode, Integer insertCode, List<BannerSub> bannerSub) {
		GlobalMapper.getBannerMapper().deleteBannerSub(bannerCode);
		for (BannerSub banner : bannerSub) {
			banner.setBannerCode(bannerCode);
			banner.setInsertCode(insertCode);
			GlobalMapper.getBannerMapper().saveBannerSub(banner);
		}
	}

	@Override
	public List<List<BannerPlaceTag>> getPlaceTag(List<BannerPlaceTagCond> cond) {
		List<List<BannerPlaceTag>> bannerPlaceList = new ArrayList<List<BannerPlaceTag>>();

		for (BannerPlaceTagCond tag : cond) {
			tag.setRadiusType(getRadiusType(tag.getRadiusType()));
			bannerPlaceList.add(GlobalMapper.getBannerMapper().getPlaceTag(tag));
		}
		return bannerPlaceList;
	}

	@Override
	public Integer getRadiusType(Integer type) {
		return GlobalMapper.getBannerMapper().getRadiusType(type);
	}

	@Override
	public List<Banner> getSimpleTempBanner(BannerCond cond) {
		return GlobalMapper.getBannerMapper().getSimpleTempBanner(cond);
	}

	@Override
	public Map<String, Object> getBanner(Integer bannerCode) {

		List<String> bannerKeyword = GlobalMapper.getBannerMapper().getBannerKeyword(bannerCode);
		List<BannerPlace> bannerPlace = GlobalMapper.getBannerMapper().getBannerPlace(bannerCode);
		List<BannerPlaceSquare> bannerPlaceSquare = GlobalMapper.getBannerMapper().getBannerPlaceSquare(bannerCode);
		List<BannerSub> bannerSub = GlobalMapper.getBannerMapper().getBannerSub(bannerCode);

		Map<String, Object> info = new HashedMap<>();

		info.put("keywords", bannerKeyword);
		info.put("places", bannerPlace);
		info.put("placeSquare", bannerPlaceSquare);
		info.put("bannerSub", bannerSub);

		return info;
	}

	@Transactional
	@Override
	public ResultData saveBannerStatus(Banner data) throws Exception {
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		try {
			GlobalMapper.getBannerMapper().saveBannerStatus(data);
			
			if (data.getStatus() == BANNER_SERVICE_OFF) {
				GlobalMapper.getMediaShopMapper().deleteLocalBoxBanner(data.getBannerCode());
			}
			else if (data.getStatus() == BANNER_SERVICE_ON) {
				data.setLocalboxCode(GlobalMapper.getMediaShopMapper().getLocalBoxCode(data.getInsertCode()));
				
				if(data.getLocalboxCode() != null) {
					Short seq = (short) (GlobalMapper.getMediaShopMapper().getLocalBoxBannerCount(data.getInsertCode()).shortValue() + 1);
				
					data.setSeq(seq);
					GlobalMapper.getMediaShopMapper().saveLocalboxBanner(data);
				}
			}
		} catch (Exception ex) {
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
		}
		return rtn;
	}
	
	@Transactional
	@Override
	public ResultData delete(BannerCond cond) throws Exception {
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();

		try {
			Integer bannerCode = cond.getBannerCode();
			GlobalMapper.getBannerMapper().delete(bannerCode);
		} catch (Exception ex) {
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
		}

		return rtn;
	}

	@Override
	public BannerShareList getReqBannerShareList(BannerCond cond) {
		BannerShareList shares = new BannerShareList();
		shares.setShares(GlobalMapper.getBannerMapper().getReqBannerShareList(cond));
		shares.setTotalNum(GlobalMapper.getBannerMapper().getTotalBannerNum());

		return shares;
	}

	@Override
	public BannerShareList getResBannerShareList(BannerCond cond) {
		BannerShareList shares = new BannerShareList();
		shares.setShares(GlobalMapper.getBannerMapper().getResBannerShareList(cond));
		shares.setTotalNum(GlobalMapper.getBannerMapper().getTotalBannerNum());

		return shares;
	}
}
