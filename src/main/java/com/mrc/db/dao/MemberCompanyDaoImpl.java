package com.mrc.db.dao;

import com.mrc.db.dao.interfaces.MemberCompanyDao;
import com.mrc.db.dto.common.ResultData;
import com.mrc.db.dto.common.ResultType;
import com.mrc.db.dto.member.MemberCond;
import com.mrc.db.dto.member.Member;
import com.mrc.db.dto.member.MemberCompany;
import com.mrc.db.mapper.GlobalMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

/**
 * Created by mrc0700@gmail.com on 2020-11-23
 * Discription :
 */
@Repository
public class MemberCompanyDaoImpl extends BaseDaoImpl implements MemberCompanyDao<MemberCond, MemberCompany> {
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public List<MemberCompany> getList(MemberCond cond) {
        return GlobalMapper.getMemberCompanyMapper().getList(cond);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public MemberCompany getData(MemberCond cond) {
        List<MemberCompany> list = GlobalMapper.getMemberCompanyMapper().getList(cond);
        if(list.size() == 1) return list.get(0);
        return null;
    }
    @Transactional
    @Override
    public ResultData saveList(List<MemberCompany> list) {
        // TODO Auto-generated method stub
        ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
        try {
            for (int i = 0; i < list.size(); i++) {
                GlobalMapper.getMemberCompanyMapper().save(list.get(i));
            }
        } catch (Exception ex) {
            rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return rtn;
    }
    @Transactional
    @Override
    public ResultData save(MemberCompany data) {
        ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
        try {
            GlobalMapper.getMemberCompanyMapper().save(data);
        } catch (Exception ex) {
            rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return rtn;
    }

    @Transactional
    @Override
    public ResultData delete(MemberCompany data) {
        ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
        try {
            GlobalMapper.getMemberCompanyMapper().delete(data);
        } catch (Exception ex) {
            rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return rtn;
    }

    @Override
    public ResultData changeIndividual(Member data) {
        ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
        try {
            GlobalMapper.getMemberCompanyMapper().changeIndividual(data);
        } catch (Exception ex) {
            rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return rtn;
    }
}
