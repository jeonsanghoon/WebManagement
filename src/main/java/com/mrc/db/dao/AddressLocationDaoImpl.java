package com.mrc.db.dao;

import com.mrc.db.dao.interfaces.AddressLocationDao;
import com.mrc.db.dto.address.*;
import com.mrc.db.dto.common.ResultData;
import com.mrc.db.dto.common.ResultType;
import com.mrc.db.mapper.GlobalMapper;
import com.mrc.scheduling.LoadDataTxtFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Repository
public class AddressLocationDaoImpl extends BaseDaoImpl implements AddressLocationDao<AddressLocationCond, AddressLocation> {

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public List<AddressLocation> getList(AddressLocationCond cond) {
		// TODO Auto-generated method stub
		if(cond.getPageSize()  == null) cond.setPageSize(10);
		if(cond.getPage()  != null )
		{
			if(cond.getStartRow() == null) cond.setStartRow((cond.getPage() -1) * cond.getPageSize());
		}
		return GlobalMapper.getAddressLocationMapper().getList(cond);
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public AddressLocation getData(AddressLocationCond cond) {
		// TODO Auto-generated method stub
		cond.setStartRow(0);
		cond.setPageSize(10);
		List<AddressLocation> list = GlobalMapper.getAddressLocationMapper().getList(cond);
		if(list.size() == 1) return list.get(0);
		else
			return null;
	}

	@Transactional
	@Override
	public ResultData saveList(List<AddressLocation> list) {
		// TODO Auto-generated method stub
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		try {
			for (int i = 0; i < list.size(); i++) {
				GlobalMapper.getAddressLocationMapper().save(list.get(i));
			}
		} catch (Exception ex) {
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return rtn;
	}

	@Transactional
	@Override
	public ResultData save(AddressLocation data) {
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		try {
				GlobalMapper.getAddressLocationMapper().save(data);
		} catch (Exception ex) {
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return rtn;
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public List<LocationDong> getCurrentDong(AddressLocationCond cond) {
		// TODO Auto-generated method stub
		List<LocationDong> rtn = GlobalMapper.getAddressLocationMapper().getCurrentDong(cond);
		return rtn;
	}
	@Transactional
	@Override
	public ResultData addressLocation1Save(AddressLocation1 data) {
		// TODO Auto-generated method stub
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		try {
			rtn.data = GlobalMapper.getAddressLocation1Mapper().save(data).toString();
		} catch (Exception ex) {
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return rtn;
	}
	@Autowired
	private LoadDataTxtFile loadDataTxtFile;

	@Override
	public List<AddressLocationBase> getAddressLocationBaseListAftertextFilaSave(String path) throws Exception {


		GlobalMapper.getAddressLocationBaseMapper().deleteTmpOriginAddressTxtfile();
		//GlobalMapper.getAddressLocationBaseMapper().addTmpOrignAddressTxtfileByTextfile(path);
		loadDataTxtFile.exec(path);
		GlobalMapper.getAddressLocationBaseMapper().addAddressLocationBaseByTmpOrignAddressTxtfile();
		AddressLocationBaseCond cond = AddressLocationBaseCond.builder().isNullSearchLatitude(true).build();
		return GlobalMapper.getAddressLocationBaseMapper().getList(cond);
	}
}

