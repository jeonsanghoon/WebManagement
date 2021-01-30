package com.mrc.db.dao.interfaces;

import com.mrc.db.dto.common.ResultData;

public interface MemberBookmarkDao<T> extends BaseDao<T,T> {
	ResultData delete(T data);
}
