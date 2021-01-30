package com.mrc.db.dao;

import com.mrc.db.dao.interfaces.FileDao;
import com.mrc.db.dto.common.ResultData;
import com.mrc.db.dto.common.ResultType;
import com.mrc.db.dto.file.FileCond;
import com.mrc.db.dto.file.File;
import com.mrc.db.mapper.GlobalMapper;
import com.mrc.framework.Global;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Repository
public class FileDaoImpl extends BaseDaoImpl implements FileDao<FileCond, File> {

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public List<File> getList(FileCond cond) {
		// TODO Auto-generated method stub
		return GlobalMapper.getFileMapper().getList(cond);
		
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public File getData(FileCond cond) {
		// TODO Auto-generated method stub
	  List<File> list = GlobalMapper.getFileMapper().getList(cond);
	  if(list.size() == 1) return list.get(0);
	  else return null;
	}

	@Transactional
	@Override
	public ResultData saveList(List<File> list) {
		// TODO Auto-generated method stub
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		try {
			for (int i = 0; i < list.size(); i++) {
				GlobalMapper.getFileMapper().save(list.get(i));
			}
		} catch (Exception ex) {
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return rtn;
	}

	@Transactional
	@Override
	public ResultData save(File data) {
		// TODO Auto-generated method stub
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		try {
			if(data.getFileExt() == null) data.setFileExt(data.getFileExt().split(".")[1]);
			GlobalMapper.getFileMapper().save(data);
		} catch (Exception ex) {
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return rtn;
	}

	@Transactional
	@Override
	public ResultData delete(File data) {
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		try {
			Global.getFileInfo().fileDelete(Global.getFileInfo().getRealFilePath(data.getFileUrl()));
			GlobalMapper.getFileMapper().delete(data);
		} catch (Exception ex) {
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return rtn;
	}
}
