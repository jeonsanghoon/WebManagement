package com.mrc.db.dao;

import com.mrc.db.dao.interfaces.SignageDao;
import com.mrc.db.dto.common.ResultData;
import com.mrc.db.dto.common.ResultType;
import com.mrc.db.dto.signage.*;
import com.mrc.db.mapper.GlobalMapper;
import com.mrc.framework.Global;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 사이니지 DAO
 */
@Repository
public class SignageDaoImpl extends BaseDaoImpl implements SignageDao<SignageCond, Signage> {

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public List<Signage> getList(SignageCond cond) {

		List<Signage> rtn = GlobalMapper.getSignageMapper().getList(cond);
		return rtn;
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public Signage getData(SignageCond cond) {
		// TODO Auto-generated method stub
		List<Signage> list = GlobalMapper.getSignageMapper().getList(cond);
		if(list.size() == 1) return list.get(0);

		return null;
	}

	@Transactional
	@Override
	public ResultData saveList(List<Signage> list) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public ResultData save(Signage data) {
		// TODO Auto-generated method stub
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		try {
			if(data.getSignCode() <=0) data.setSignCode(null);
			GlobalMapper.getSignageMapper().save(data);
			rtn.data = data.getSignCode().toString();
			if(data.getPlaceList() != null)
			{
				GlobalMapper.getSignagePlaceMapper().delete(SignagePlace.builder().signCode(data.getSignCode()).build());
				for(SignagePlace place : data.getPlaceList())
				{
					place.setInsertCode(data.getInsertCode());
					place.setSignCode(data.getSignCode());
					GlobalMapper.getSignagePlaceMapper().save(place);
				}
			}
		} catch (Exception ex) {
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return rtn;
	}

	@Transactional
	@Override
	public ResultData delete(Signage data) {
		// TODO Auto-generated method stub
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		try {
			GlobalMapper.getSignagePlaceMapper().delete(SignagePlace.builder().signCode(data.getSignCode()).build());
			GlobalMapper.getSignageMapper().delete(data);
		} catch (Exception ex) {
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return rtn;
	}

	@Transactional
	@Override
	public ResultData update(Signage data) {
		// TODO Auto-generated method stub
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		try {
			GlobalMapper.getSignageMapper().update(data);
			if(data.getPlaceList() != null)
			{
				GlobalMapper.getSignagePlaceMapper().delete(SignagePlace.builder().signCode(data.getSignCode()).build());
				for(SignagePlace place : data.getPlaceList())
				{
					place.setInsertCode(data.getInsertCode());
					place.setSignCode(data.getSignCode());
					GlobalMapper.getSignagePlaceMapper().save(place);
				}
			}

		} catch (Exception ex) {
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return rtn;
	}

	@Transactional
	@Override
	public ResultData changeStatus(SignageStatus data) {
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		try {
			GlobalMapper.getSignageMapper().changeStatus(data);

		} catch (Exception ex) {
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return rtn;
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public List<SignagePlace> getSignagePlace(SignageCond cond) {
		// TODO Auto-generated method stub
		return GlobalMapper.getSignagePlaceMapper().getList(cond);
	}

	@Transactional
	@Override
	public ResultData signagePlaceSave(SignagePlace data) {
		// TODO Auto-generated method stub
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		try {
			if(data.getPlaceType() == null) data.setPlaceType(1);
			GlobalMapper.getSignagePlaceMapper().save(data);
			rtn.data = data.getPlaceCode().toString();

		} catch (Exception ex) {
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return rtn;
	}

	@Transactional
	@Override
	public ResultData signagePlaceDelete(SignagePlace data) {
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		try {
			GlobalMapper.getSignagePlaceMapper().delete(data);
			rtn.data = data.getPlaceCode().toString();

		} catch (Exception ex) {
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return rtn;
	}
	@Transactional
	@Override
	public ResultData signageBannerListSave(List<SignageBanner> list ) {
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		try {
			GlobalMapper.getSignageBannerMapper().saveList(list);
		}catch (Exception ex) {
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return rtn;
	}

	@Transactional
	@Override
	public ResultData signageBannerListDelete(List<SignageBanner> list) {
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		try {
			GlobalMapper.getSignageBannerMapper().deleteList(list);

			List<SignageBanner> rtn2 = Global.getDataInfo().distinctList(list, SignageBanner::getSignCode, SignageBanner::getPage);
			for(SignageBanner data : rtn2)
			{
				GlobalMapper.getSignageBannerMapper().arrangeAfterSave(data);
			}
			//list.stream().collect(Collectors.groupingBy(p -> p.getSignCode())).values().stream().map(l -> (l.get(0)));

		}catch (Exception ex) {
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return rtn;
	}


	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public List<SignageBannerList> getSignageBannerList(SignageBanner Cond)
	{
		return GlobalMapper.getSignageBannerMapper().getList(Cond);
	}
}
