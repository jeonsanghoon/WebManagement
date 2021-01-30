package com.mrc.db.dao;

import com.mrc.db.dao.interfaces.BoardDao;
import com.mrc.db.dto.board.BoardCond;
import com.mrc.db.dto.board.Board;
import com.mrc.db.dto.board.BoardRead;
import com.mrc.db.dto.common.ResultData;
import com.mrc.db.dto.common.UploadFiles;
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
public class BoardDaoImpl extends BaseDaoImpl implements BoardDao<BoardCond, Board, BoardRead> {

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public List<Board> getList(BoardCond cond) {
		// TODO Auto-generated method stub
		if (cond.getMemberCode() != null) {
			BoardRead condRead = BoardRead.builder()
											.boardType(cond.getBoardType())
											.insertCode(cond.getMemberCode()).build();

			List<BoardRead> list = GlobalMapper.getBoardMapper().getReadList(condRead);
			if (list.size() > 0
					&& !Global.getDataInfo().isNullEmpty(list.get(0).getBoardCodes()))

				cond.setBoardCodes(list.get(0).getBoardCodes());
		}

	   	if(Global.getDataInfo().isNullEmpty(cond.getSortBy()))
	   		cond.setSortBy("a.board_code");
	   	else{
	   		if(cond.getSortBy().indexOf("insert_name") >= 0)
				cond.setSortBy(cond.getSortBy().replaceAll("insert_name","b.member_name"));
	   		else if(cond.getSortBy().indexOf("a.") <0)
	   			cond.setSortBy(("a.").concat(cond.getSortBy()));
		}

		List<Board> rtn = GlobalMapper.getBoardMapper().getList(cond);
		Integer total = GlobalMapper.getCommonMapper().getTotRows();
		if(rtn.size() > 0)
		{
			rtn.get(0).setTotRows(total);
		}
		return rtn;
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public Board getData(BoardCond cond) {
		// TODO Auto-generated method stub

		List<Board> list = GlobalMapper.getBoardMapper().getList(cond);
		if (list.size() == 1)
			return list.get(0);
		else
			return null;
	}

	@Transactional
	@Override
	public ResultData saveList(List<Board> list) {
		// TODO Auto-generated method stub
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		try {
			for (int i = 0; i < list.size(); i++) {
				GlobalMapper.getBoardMapper().save(list.get(i));
			}
		} catch (Exception ex) {
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return rtn;
	}

	@Transactional
	@Override
	public ResultData save(Board data) {
		// TODO Auto-generated method stub
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		try {
			Integer boardCode = data.getBoardCode();
			GlobalMapper.getBoardMapper().save(data);
			if(boardCode <= 0) boardCode = data.getBoardCode();

			if(data.getUploadFiles() != null &&data.getUploadFiles().size() >0) {

				this.fileSave(data.getUploadFiles(),boardCode,data.getInsertCode());
			}
			rtn.setData(boardCode.toString());
		} catch (Exception ex) {
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return rtn;
	}

	/**
	 * 파일저장
	 * @param files
	 * @param boardCode
	 * @param insertCode
	 */
	private void fileSave(List<UploadFiles> files, Integer boardCode, Integer insertCode)
	{
		for(UploadFiles fData : files) {
			File file = File.builder().tableName("t_board")
					.tableKey(boardCode.toString())
					.fileName(fData.getOriFileName())
					.fileUrl(fData.getAttachmentUrl())
					.fileSize(Integer.parseInt(fData.getFileSize().toString()))
					.fileExt(fData.getFileContentType())
					.insertCode(insertCode)
					.fileSeq(0)
					.build();
			GlobalMapper.getFileMapper().save(file);
		}
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public List<BoardRead> getReadList(BoardRead cond) {
		// TODO Auto-generated method stub
		return GlobalMapper.getBoardMapper().getReadList(cond);
	}

	@Transactional
	@Override
	public ResultData readUpdate(BoardRead data) {
		// TODO Auto-generated method stub
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		try {
			GlobalMapper.getBoardMapper().read_update(data);
		} catch (Exception ex) {
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return rtn;
	}

	@Transactional
	@Override
	public ResultData delete(Board data) {
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		try {
			List<File> files = GlobalMapper.getFileMapper().getList(FileCond.builder().tableName("t_board").tableKey(data.getBoardCode().toString()).build());
			for(File file : files)
			{
				Global.getFileInfo().fileDelete(Global.getFileInfo().getRealFilePath(file.getFileUrl()));
				GlobalMapper.getFileMapper().delete(file);
			}
			GlobalMapper.getBoardMapper().delete(data);
		} catch (Exception ex) {
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return rtn;
	}
}
