package com.mrc.db.dao.interfaces;

import com.mrc.db.dto.common.ResultData;

public interface FileDao<K,V> extends BaseDao<K,V> {
    ResultData delete(V data);
}

