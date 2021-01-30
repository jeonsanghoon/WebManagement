package com.mrc.db.dao;

import com.mrc.db.dao.interfaces.GlobalDao;
import com.mrc.db.dao.interfaces.MediaShopDao;
import com.mrc.db.dto.common.ResultData;
import com.mrc.db.dto.common.ResultType;
import com.mrc.db.dto.keyword.Keyword;
import com.mrc.db.dto.mediaShop.*;
import com.mrc.db.mapper.GlobalMapper;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kyh
 *
 * 2020.07.29
 */


@Repository
public class MediaShopDaoImpl extends BaseDaoImpl implements MediaShopDao<LocalboxCond, Localbox> {
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public Map<String, Object> getLocalBox(LocalboxCond cond) {
		Localbox localBox = GlobalMapper.getMediaShopMapper().getLocalBox(cond);
		
		cond.setLocalboxCode(localBox.getLocalboxCode());
		
		List<String> localBoxKeyword = GlobalMapper.getMediaShopMapper().getLocalBoxKeyword(cond);
		LocalboxPlace localBoxPlace = GlobalMapper.getMediaShopMapper().getLocalBoxPlace(cond);
		
		Map<String, Object> info = new HashedMap<>();

		info.put("localBox", localBox);
		info.put("keywords", localBoxKeyword);
		info.put("place", localBoxPlace);

		return info;
	}
	
	@Transactional
	@Override
	public ResultData save(Localbox localbox, List<Keyword> keywords, LocalboxPlace place) {
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		try {
			Integer localboxCode = localbox.getLocalboxCode();
			Integer insertCode = localbox.getInsertCode();

			GlobalMapper.getMediaShopMapper().save(localbox);

			if (localboxCode == null) {
				localboxCode = localbox.getLocalboxCode();
			}

			rtn.setData(localboxCode.toString());

			this.saveLocalboxPlace(localboxCode, insertCode, place);
			
			if (keywords != null) {
				this.saveLocalboxKeyword(localboxCode, insertCode, saveKeyword(keywords));
			}
		} catch (Exception ex) {
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return rtn;
	}



	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public LocalboxSimple getLocalBoxSimple(LocalboxCond cond) {
		return GlobalMapper.getMediaShopMapper().getLocalBoxSimple(cond);
	}
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public Map<String, Object> getLocalBoxBannerList(LocalboxCond cond) {
		List<LocalboxBanner> bannerList = GlobalMapper.getMediaShopMapper().getLocalBoxBannerList(cond);
		Integer totalNum = GlobalMapper.getMediaShopMapper().getTotalNum();
		
		Map<String, Object> mapper = new HashMap<String, Object>();

		mapper.put("bannerList", bannerList);
		mapper.put("totalNum", totalNum);
		
		return mapper;
	}
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public Map<String, Object> getLocalBoxPreviewBannerList(LocalboxCond cond) {
		List<LocalboxBanner> bannerList = GlobalMapper.getMediaShopMapper().getLocalBoxPreviewBannerList(cond);
		Integer totalNum = GlobalMapper.getMediaShopMapper().getTotalNum();
		
		List<Integer> bannerCodes = new ArrayList<Integer>();
		
		for(LocalboxBanner banner : bannerList) {
			bannerCodes.add(banner.getBannerCode());
		}
		
		cond.setBannerCodes(bannerCodes);
		
		List<LocalboxPlace> places = GlobalMapper.getMediaShopMapper().getLocalBoxBannerPlaceList(cond);
		
		Map<String, Object> mapper = new HashMap<String, Object>();

		mapper.put("bannerList", bannerList);
		mapper.put("totalNum", totalNum);
		mapper.put("places", places);
		
		return mapper;
	}
	@Transactional
	@Override
	public ResultData updateLocalBoxBanner(List<LocalboxBanner> banners) {
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		
		try {
			for(LocalboxBanner banner : banners) {
				GlobalMapper.getMediaShopMapper().updateLocalBoxBanner(banner);
			}
		} catch (Exception ex) {
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
		return rtn;
	}
	@Override
	public Integer getLocalBoxCode(LocalboxCond cond) {
		return GlobalMapper.getMediaShopMapper().getLocalBoxCode(cond);
	}
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public Map<String, Object> getLocalerList(LocalboxCond cond) {
		List<Localer> localers = GlobalMapper.getMediaShopMapper().getLocalerList(cond);
		Integer totalNum = GlobalMapper.getMediaShopMapper().getTotalNum();
		
		Map<String, Object> mapper = new HashMap<String, Object>();
		
		mapper.put("localers", localers);
		mapper.put("totalNum", totalNum);
		
		return mapper;
	}
	
	@Transactional
	@Override
	public ResultData saveBannerShare(List<LocalboxBannerRequest> data) {
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		
		try 
		{
			for (LocalboxBannerRequest reqBanner : data) {
				GlobalMapper.getMediaShopMapper().deleteBannerShare(reqBanner);
				
				if(reqBanner.getIsShare()) {
					List<Integer> localers = reqBanner.getLocalers();
					for(Integer approvalMemeberCode : localers) {
						reqBanner.setApprovalMemberCode(approvalMemeberCode);
						GlobalMapper.getMediaShopMapper().saveBannerShare(reqBanner);
					}
				} 
				else { 
					GlobalMapper.getMediaShopMapper().saveBannerShare(reqBanner);
				}
			}
		}
		catch (Exception ex) {
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
		return rtn;
	}
	
	@Override
	public List<LocalboxBannerRequest> getBannerShare(LocalboxCond cond) {
		return GlobalMapper.getMediaShopMapper().getBannerShare(cond);
	}

		
	@Override
	public List<Localbox> getList(LocalboxCond cond) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Localbox getData(LocalboxCond cond) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultData saveList(List<Localbox> list) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultData save(Localbox data) {
		// TODO Auto-generated method stub
		return null;
	}

	private List<Integer> saveKeyword(List<Keyword> keywords)  {
		List<Integer> keywordCodes = new ArrayList<Integer>();

		for (Keyword keyword : keywords) {
			GlobalMapper.getKeywordMapper().save(keyword);
			keywordCodes.add(keyword.getKeywordCode());
		}
		return keywordCodes;
	}

	private void saveLocalboxKeyword(Integer localboxCode, Integer insertCode, List<Integer> keywrodCodes) throws Exception {

		LocalboxKeyword localboxKeyword = new LocalboxKeyword();
		localboxKeyword.setLocalboxCode(localboxCode);
		localboxKeyword.setInsertCode(insertCode);
		GlobalMapper.getMediaShopMapper().deleteLocalboxKeyword(localboxCode);

		for (Integer keywordCode : keywrodCodes) {
			localboxKeyword.setKeywordCode(keywordCode);
			GlobalMapper.getMediaShopMapper().saveLocalboxKeyword(localboxKeyword);
		}
	}


	private void saveLocalboxPlace(Integer localboxCode, Integer insertCode, LocalboxPlace place) {
		place.setLocalboxCode(localboxCode);
		place.setInsertCode(insertCode);

		GlobalMapper.getMediaShopMapper().deleteLocalboxPlace(place);
		GlobalMapper.getMediaShopMapper().saveLocalboxPlace(place);
	}
}
