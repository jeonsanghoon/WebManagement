package com.mrc.db.mapper;


import com.mrc.db.dto.member.MemberCond;
import com.mrc.db.dto.member.Member;
import com.mrc.db.dto.member.MemberCompany;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by mrc0700@gmail.com on 2020-11-23
 * Discription :
 */
@Mapper
public interface MemberCompanyMapper {
    List<MemberCompany> getList(MemberCond cond);
    void save(MemberCompany data);
    void delete(MemberCompany data);
    void changeIndividual (Member data);
}
