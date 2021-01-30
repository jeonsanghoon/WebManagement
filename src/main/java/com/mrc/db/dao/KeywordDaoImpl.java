package com.mrc.db.dao;

import com.mrc.db.dao.interfaces.KeywordDao;
import com.mrc.db.dto.common.ResultData;
import com.mrc.db.dto.common.ResultType;
import com.mrc.db.dto.keyword.KeywordCond;
import com.mrc.db.dto.keyword.Keyword;
import com.mrc.db.mapper.GlobalMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

/**
 * @Author: kyh
 * @Date: 2020. 4. 2.
 */

@Repository
public class KeywordDaoImpl extends BaseDaoImpl implements KeywordDao<KeywordCond, Keyword> {
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public List<Keyword> getList(KeywordCond cond) {
		// TODO Auto-generated method stub
		return GlobalMapper.getKeywordMapper().getList(cond);
	}
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public List<Keyword> getAutoList(KeywordCond cond) {
		// TODO Auto-generated method stub
		return GlobalMapper.getKeywordMapper().getAutoList(cond);
	}
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public Keyword getData(KeywordCond cond) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultData saveList(List<Keyword> list) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public ResultData save(Keyword data) {
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		try {
			GlobalMapper.getKeywordMapper().save(data);
			rtn.data = data.getKeywordCode().toString();
		} catch (Exception ex) {
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return rtn;
	}
}
