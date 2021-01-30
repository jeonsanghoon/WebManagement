package com.mrc.db.dao.interfaces;


import com.mrc.db.dto.common.ResultData;
import com.mrc.db.dto.member.Member;

/**
 * Created by mrc0700@gmail.com on 2020-11-23
 * Discription :
 */
public interface MemberCompanyDao<K,V> extends BaseDao<K,V> {
    ResultData delete(V data);
    ResultData changeIndividual(Member data);
}
