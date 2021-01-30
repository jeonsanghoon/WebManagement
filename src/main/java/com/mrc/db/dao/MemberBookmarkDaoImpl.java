package com.mrc.db.dao;

import com.mrc.db.dao.interfaces.MemberBookmarkDao;
import com.mrc.db.dto.common.ResultData;
import com.mrc.db.dto.common.ResultType;
import com.mrc.db.dto.member.MemberBookmark;
import com.mrc.db.mapper.GlobalMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Repository
public class MemberBookmarkDaoImpl extends BaseDaoImpl implements MemberBookmarkDao<MemberBookmark> {

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public List<MemberBookmark> getList(MemberBookmark cond) {
		// TODO Auto-generated method stub
		return GlobalMapper.getMemberBookmarkMapper().getList(cond);
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public MemberBookmark getData(MemberBookmark cond) {
		// TODO Auto-generated method stub
		List<MemberBookmark> list = GlobalMapper.getMemberBookmarkMapper().getList(cond);
		if(list.size() == 0) return list.get(0);
		else return null;
	}
	@Transactional
	@Override
	public ResultData saveList(List<MemberBookmark> list) {
		// TODO Auto-generated method stub
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		try {
			for(MemberBookmark data : list) {
				GlobalMapper.getMemberBookmarkMapper().delete(data);
				GlobalMapper.getMemberBookmarkMapper().save(data);
			}
		} catch (Exception ex) {
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return rtn;
	}
	@Transactional
	@Override
	public ResultData save(MemberBookmark data) {
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		try {
			GlobalMapper.getMemberBookmarkMapper().delete(data);
			GlobalMapper.getMemberBookmarkMapper().save(data);
		} catch (Exception ex) {
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return rtn;
	}

	@Transactional
	@Override
	public ResultData delete(MemberBookmark data) {
		// TODO Auto-generated method stub
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		try {
			if(data.getBookmarkCode() == null )
			{
				if(data.getMemberCode()== null)return  ResultData.builder().resultType(ResultType.Error).message("회원 정보가 없습니다.").build();;
				if(		data.getBookmarkType() == null ||
						(data.getSignCode() == null && data.getBannerCode() == null && data.getLocalboxCode() == null))
				{
					throw new Exception("등록정보가 없습니다.");
				}
			}
			GlobalMapper.getMemberBookmarkMapper().delete(data);
		}catch (Exception ex) {
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return rtn;
	}
}
