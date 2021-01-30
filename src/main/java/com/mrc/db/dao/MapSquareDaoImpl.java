package com.mrc.db.dao;

import com.mrc.db.dao.interfaces.MapSquareDao;
import com.mrc.db.dto.address.MapSquareCond;
import com.mrc.db.dto.address.MapSquare;
import com.mrc.db.dto.common.ResultData;
import com.mrc.db.dto.common.ResultType;
import com.mrc.db.mapper.GlobalMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Repository
public class MapSquareDaoImpl extends BaseDaoImpl implements MapSquareDao<MapSquareCond, MapSquare> {

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public List<MapSquare> getList(MapSquareCond cond) {
		// TODO Auto-generated method stub
		return GlobalMapper.getMapSquareMapper().getList(cond);
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public MapSquare getData(MapSquareCond cond) {
		// TODO Auto-generated method stub
		List<MapSquare> list = GlobalMapper.getMapSquareMapper().getList(cond);
		if(list.size() == 1) return list.get(0);
		else return null;
	}

	@Transactional
	@Override
	public ResultData saveList(List<MapSquare> list) {
		// TODO Auto-generated method stub
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		try {
			for (int i = 0; i < list.size(); i++) {
				GlobalMapper.getMapSquareMapper().save(list.get(i));
			}
		} catch (Exception ex) {
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return rtn;
	}

	@Transactional
	@Override
	public ResultData save(MapSquare data) {
		// TODO Auto-generated method stub
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		try {
			GlobalMapper.getMapSquareMapper().save(data);
		} catch (Exception ex) {
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return rtn;
	}
}
