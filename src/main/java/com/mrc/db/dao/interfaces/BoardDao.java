package com.mrc.db.dao.interfaces;

import java.util.List;

import com.mrc.db.dto.common.ResultData;

public interface BoardDao<K, V, t_board_read> extends BaseDao<K,V> {
	List<t_board_read> getReadList(t_board_read cond);
	ResultData readUpdate(t_board_read data);
	ResultData delete(V data);
}
