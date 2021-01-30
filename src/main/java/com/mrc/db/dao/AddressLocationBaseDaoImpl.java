package com.mrc.db.dao;

import com.mrc.db.dao.interfaces.AddressLocationBaseDao;
import com.mrc.db.dto.address.AddressLocationBase;
import com.mrc.db.dto.address.AddressLocationBaseCond;
import com.mrc.db.dto.common.ResultData;
import com.mrc.db.dto.common.ResultType;
import com.mrc.db.mapper.GlobalMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

/**
 * Created by mrc0700@gmail.com on 2021-01-13
 * Discription :
 */
@Repository
public class AddressLocationBaseDaoImpl implements AddressLocationBaseDao<AddressLocationBaseCond,AddressLocationBase>  {
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public List<AddressLocationBase> getList(AddressLocationBaseCond cond) {
        return GlobalMapper.getAddressLocationBaseMapper().getList(cond);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public AddressLocationBase getData(AddressLocationBaseCond cond) {
        List<AddressLocationBase> list = GlobalMapper.getAddressLocationBaseMapper().getList(cond);
        if(list.size() != 0) return null;
        return list.get(0);
    }
    @Transactional
    @Override
    public ResultData saveList(List<AddressLocationBase> list) {
        ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
        try {
            GlobalMapper.getAddressLocationBaseMapper().saveList(list);
        } catch (Exception ex) {
            rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return rtn;
    }
    @Transactional
    @Override
    public ResultData save(AddressLocationBase data) {
        ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
        try {
            GlobalMapper.getAddressLocationBaseMapper().save(data);
        } catch (Exception ex) {
            rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return rtn;
    }

    @Transactional
    @Override
    public ResultData update(AddressLocationBase data) {
        ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
        try {
            GlobalMapper.getAddressLocationBaseMapper().update(data);
        } catch (Exception ex) {
            rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return rtn;
    }
    @Transactional
    @Override
    public ResultData updateList(List<AddressLocationBase> list) {
        ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
        try {
            GlobalMapper.getAddressLocationBaseMapper().updateList(list);
        } catch (Exception ex) {
            rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return rtn;
    }
    @Transactional
    @Override
    public ResultData delete(AddressLocationBase data) {
        ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
        try {
            GlobalMapper.getAddressLocationBaseMapper().delete(data);
        } catch (Exception ex) {
            rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return rtn;
    }

    @Transactional
    @Override
    public ResultData autoAddAddressAreaByAddressLocationBase() {
        ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
        try {
            GlobalMapper.getAddressLocationBaseMapper().autoAddAddressAreaByAddressLocationBase();
        } catch (Exception ex) {
            rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return rtn;
    }
}
