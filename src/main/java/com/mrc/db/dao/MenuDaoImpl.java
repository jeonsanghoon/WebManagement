package com.mrc.db.dao;

import com.mrc.db.dao.interfaces.MenuDao;
import com.mrc.db.dto.common.ResultData;
import com.mrc.db.dto.common.ResultType;
import com.mrc.db.dto.menu.MenuCond;
import com.mrc.db.dto.menu.Menu;
import com.mrc.db.mapper.GlobalMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Repository
public class MenuDaoImpl extends BaseDaoImpl implements MenuDao<MenuCond, Menu> {

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public List<Menu> getList(MenuCond cond) {
		// TODO Auto-generated method stub
		List<Menu> rtn = GlobalMapper.getMenuMapper().getList(cond);
		return rtn;
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public Menu getData(MenuCond cond) {
		// TODO Auto-generated method stub
		List<Menu> list = GlobalMapper.getMenuMapper().getList(cond);
		if(list.size() == 1) return list.get(0);
		return list.get(0);
	}

	@Transactional
	@Override
	public ResultData saveList(List<Menu> list) {
		// TODO Auto-generated method stub
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		try {
			
			for (int i = 0; i < list.size(); i++) {
			    
				GlobalMapper.getMenuMapper().save(this.setSaveParam(list.get(i)));
			}
		} catch (Exception ex) {
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return rtn;
	}
	
	private Menu setSaveParam(Menu data) {
		data.setMenuLevel((int) Math.ceil(data.getSearchCode().length() / 3.00));
		if(data.getSearchCode().length() == 3)
		{
			data.setSearchCode(data.getSearchCode().replace("|", ""));
		}
		return data;
	}

	@Transactional
	@Override
	public ResultData save(Menu data) {
		// TODO Auto-generated method stub
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		try {
			// TODO Auto-generated method stub
			GlobalMapper.getMenuMapper().save(this.setSaveParam(data));
		} catch (Exception ex) {
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		}
		return rtn;
	}

	@Transactional
	@Override
	public ResultData delete(Menu data) {
		ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
		try {
			GlobalMapper.getMenuMapper().delete(data);
			rtn = ResultData.builder().resultType(ResultType.Info).data(data.getMenuCode().toString()).build();
		} catch (Exception ex) {
			rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return rtn;
	}
}
