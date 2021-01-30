/**
 * 
 */
package com.mrc.db.dao;

import com.mrc.db.dao.interfaces.CommonDao;
import com.mrc.db.dto.address.AddressArea;
import com.mrc.db.dto.common.*;
import com.mrc.db.mapper.GlobalMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

/**
 * 공통 Dao
 * 
 * @author jsh
 *
 */
@Repository
public class CommonDaoImpl extends BaseDaoImpl implements CommonDao<commonCond, Common, SelectItem> {

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public List<Common> getList(commonCond cond) {

		List<Common> list = GlobalMapper.getCommonMapper().getList(cond);
		// return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		return list;
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public Common getData(commonCond cond) {
		// TODO Auto-generated method stub
		List<Common> list = GlobalMapper.getCommonMapper().getList(cond);
		if (list.size() == 1)
			return list.get(0);
		else
			return null;
	}

	@Transactional
	@Override
	public ResultData saveList(List<Common> list) {
		// TODO Auto-generated method stub
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		try {
			for (int i = 0; i < list.size(); i++) {
				GlobalMapper.getCommonMapper().save(list.get(i));
			}
		} catch (Exception ex) {
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return rtn;
	}

	@Transactional
	@Override
	public ResultData save(Common data) {
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		try {
			GlobalMapper.getCommonMapper().save(data);
			rtn = ResultData.builder().resultType(ResultType.Info).data(data.getCommonCode().toString()).build();
		} catch (Exception ex) {
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return rtn;
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public List<SelectItem> getSelectItemList(commonCond cond) {
		// TODO Auto-generated method stub
		List<SelectItem> rtn = GlobalMapper.getCommonMapper().getSelectItemList(cond);
		return rtn;
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public List<AddressArea> getAddressAreaList(AddressArea cond) {
		// TODO Auto-generated method stub
		List<AddressArea> rtn = GlobalMapper.getCommonMapper().getAddressAreaList(cond);
		return rtn;
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public List<String> getTableToHtml() {
		// TODO Auto-generated method stub
		return GlobalMapper.getCommonMapper().getTableToHtml();
	}

	@Transactional
	@Override
	public ResultData delete(Common data) {
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		try {
			GlobalMapper.getCommonMapper().delete(data);
			rtn = ResultData.builder().resultType(ResultType.Info).data(data.getCommonCode().toString()).build();
		} catch (Exception ex) {
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return rtn;
	}
}
