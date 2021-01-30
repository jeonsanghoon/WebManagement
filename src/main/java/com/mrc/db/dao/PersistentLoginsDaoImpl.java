package com.mrc.db.dao;

import com.mrc.db.dao.interfaces.PersistentLoginsDao;
import com.mrc.db.dto.common.ResultData;
import com.mrc.db.dto.common.ResultType;
import com.mrc.db.dto.member.PersistentLogins;
import com.mrc.db.mapper.GlobalMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Repository
public class PersistentLoginsDaoImpl extends BaseDaoImpl implements PersistentLoginsDao<PersistentLogins> {

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public List<PersistentLogins> getList(PersistentLogins cond) {
		// TODO Auto-generated method stub
		return GlobalMapper.getPersistentLoginsMapper().getTokenList(cond);
	}

	@Transactional
	@Override
	public PersistentLogins getData(PersistentLogins cond) {
		// TODO Auto-generated method stub
		
		List<PersistentLogins> list = GlobalMapper.getPersistentLoginsMapper().getTokenList(cond);
		if(list.size()==0 ) return null;
		return list.get(0);
	}

	@Transactional
	@Override
	public ResultData saveList(List<PersistentLogins> list) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public ResultData save(PersistentLogins data) {
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		try {
			PersistentLogins cond = PersistentLogins.builder().series(data.getSeries()).build();
			List<PersistentLogins> list = GlobalMapper.getPersistentLoginsMapper().getTokenList(cond);
			if (list.size() == 0) {
				GlobalMapper.getPersistentLoginsMapper().createToken(data);
			} else if (data.getSaveType().equals("D")) {
				GlobalMapper.getPersistentLoginsMapper().deleteToken(data);
			} else {
				GlobalMapper.getPersistentLoginsMapper().updateToken(data);
			}
		}catch (Exception ex) {
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return rtn;
	}
}
