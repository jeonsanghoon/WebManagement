package com.mrc.db.dao.interfaces;

import com.mrc.db.dto.common.ResultData;

/**
 * Created by jeon.sh@altsoft.kr on 2020-11-02
 * Discription :
 */
public interface MemberChannelDao<T>  extends BaseDao<T, T> {
    ResultData delete (T data);

}
