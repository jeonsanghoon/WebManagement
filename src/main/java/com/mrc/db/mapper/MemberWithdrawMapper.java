package com.mrc.db.mapper;

import com.mrc.db.dto.member.MemberWithdraw;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by jeon.sh@altsoft.kr on 2020-11-02
 * Discription :
 */
@Mapper
public interface MemberWithdrawMapper {
    List<MemberWithdraw> getList(MemberWithdraw data);
    void save(MemberWithdraw data);
    void delete(MemberWithdraw data);
    
}
