package com.mrc.db.dao.interfaces;

import java.util.List;

/**
 * @Author: kyh
 * @Date: 2020. 4. 2.
 */
public interface KeywordDao<K,V> extends BaseDao<K,V> {
	
	/**
	 * 키워드자동검색 (jsh)
	 * @param cond
	 * @return
	 */
	List<V> getAutoList(K cond);
}
