package com.mrc.db.dao;

import com.mrc.db.dao.interfaces.MemberChannelDao;
import com.mrc.db.dto.common.ResultData;
import com.mrc.db.dto.common.ResultType;
import com.mrc.db.dto.member.MemberWithdraw;
import com.mrc.db.mapper.GlobalMapper;
import com.mrc.framework.Global;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

/**
 * Created by jeon.sh@altsoft.kr on 2020-11-02
 * Discription : 회원탈퇴 DAO
 */
@Repository
public class MemberChannelDaoImpl extends BaseDaoImpl implements MemberChannelDao<MemberWithdraw> {
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public List<MemberWithdraw> getList(MemberWithdraw cond) {
        return GlobalMapper.getMemberWithdrawMapper().getList(cond);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public MemberWithdraw getData(MemberWithdraw cond) {
        List<MemberWithdraw> rtn = this.getList(cond);
        if(rtn.size() == 1) return rtn.get(0);
        else return null;
    }

    @Transactional
    @Override
    public ResultData saveList(List<MemberWithdraw> list) {

        ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
        try {
            for (MemberWithdraw data : list)
                GlobalMapper.getMemberWithdrawMapper().save(data);
        } catch (Exception ex) {
            rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return rtn;
    }

    @Transactional
    @Override
    public ResultData save(MemberWithdraw data) {
        ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
        try {
            if(data.getMemberCode() == null) data.setMemberCode(data.getInsertCode());
            if(data.getWithdrawCode() == null) data.setWithdrawCode(data.getInsertCode());

            if(data.getWaitingDay() == null) data.setWaitingDay(0);

            data.setInsertDate(Global.getDataInfo().nowTimestamp());
            data.setWithdrawDate(Global.getDataInfo().addHoursToTimestamp(data.getInsertDate(), 24 * data.getWaitingDay()));
            GlobalMapper.getMemberWithdrawMapper().save(data);

        } catch (Exception ex) {
            rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return rtn;
    }

    @Transactional
    @Override
    public ResultData delete(MemberWithdraw data) {
        ResultData rtn = ResultData.builder().resultType(ResultType.Info).build();
        try {
            GlobalMapper.getMemberWithdrawMapper().delete(data);
        } catch (Exception ex) {
            rtn = ResultData.builder().resultType(ResultType.Error).message(ex.getMessage()).build();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return rtn;
    }
}
